package pl.edu.pw.eiti.groupbuying.web.admin.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import pl.edu.pw.eiti.groupbuying.core.dao.AndroidRegisteredDeviceDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.AndroidRegisteredDevice;
import pl.edu.pw.eiti.groupbuying.web.admin.exception.InternalServerErrorException;
import pl.edu.pw.eiti.groupbuying.web.admin.model.PushNotification;
import pl.edu.pw.eiti.groupbuying.web.admin.service.PushService;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

@Service("pushService")
public class PushServiceImpl implements PushService {
	private static final Logger LOG = Logger.getLogger(PushServiceImpl.class);

	private static final int DEFAULT_ANDROID_TIME_TO_LIVE_IN_SECONDS = 12 * 3600;

	private static final int GCM_THREAD_POOL_SIZE = 5;

	private static final int MULTICAST_SIZE = 1000;

	@Value("${gcm.api.key}")
	private String apiKey;
	
	@Autowired
	private AndroidRegisteredDeviceDAO androidRegisteredDeviceDAO;

	@Override
	public void push(final PushNotification pushNotification) {

		List<Future<Exception>> androidExceptions = null;
		LOG.info("##### Push Notification START #####");
		LOG.info("######## Sending Push Notification: " + pushNotification.toString() + " ########");

		final long androidStartTime = System.currentTimeMillis();
		final List<AndroidRegisteredDevice> devices = androidRegisteredDeviceDAO.getDevices();

		LOG.info("##### Pushing for " + devices.size() + " devices ######");
		final AtomicInteger failCount = new AtomicInteger();
		androidExceptions = pushForAndroid(pushNotification, devices, failCount);

		LOG.info("##### Pushing  failed for " + failCount.get() + "/" + devices.size() + " devices ######");
		LOG.info("##### Push Notification END #####");
		long totalDuration = System.currentTimeMillis() - androidStartTime;
		String totalDurationInSeconds = Long.toString((totalDuration / 1000)) + "." + Long.toString((totalDuration % 1000));
		LOG.info("##### Total push for Android time: " + totalDurationInSeconds + " seconds #####");

		processExceptions(androidExceptions);
	}

	private void processExceptions(final List<Future<Exception>> androidExceptions) {
		StringBuilder exceptionBuilder = new StringBuilder();
		if (androidExceptions != null) {
			synchronized (androidExceptions) {
				Iterator<Future<Exception>> i = androidExceptions.iterator();
				while (i.hasNext()) {
					try {
						Exception ex = i.next().get();
						if (ex != null) {
							exceptionBuilder.append("Error(s) occured while sending Android notifications. \n");
						}
					} catch (InterruptedException e) {
						LOG.error(e.getLocalizedMessage(), e);
						exceptionBuilder.append("Error(s) occured while sending Android notifications. \n");
						break;
					} catch (ExecutionException e) {
						LOG.error(e.getLocalizedMessage(), e);
						exceptionBuilder.append("Error(s) occured while sending Android notifications. \n");
						break;
					}
				}
			}
		}

		String exceptionString = exceptionBuilder.toString();
		if (StringUtils.hasText(exceptionString)) {
			throw new InternalServerErrorException(exceptionString + "Please contact your administrator.");
		}
	}

	private List<Future<Exception>> pushForAndroid(final PushNotification pushNotification, final List<AndroidRegisteredDevice> devices, final AtomicInteger failCount) {

		final List<Future<Exception>> exceptionList = Collections.synchronizedList(new ArrayList<Future<Exception>>());
		final ExecutorService gcmExecutorService = Executors.newFixedThreadPool(GCM_THREAD_POOL_SIZE);
		final Sender sender = new Sender(apiKey);

		final int total = devices.size();
		final List<String> partialDeviceRegistrationIds = new ArrayList<String>(total);

		int counter = 0;

		final Message.Builder messageBuilder = new Message.Builder();

		messageBuilder.timeToLive(DEFAULT_ANDROID_TIME_TO_LIVE_IN_SECONDS);
		messageBuilder.addData("message", pushNotification.getMessage());
		messageBuilder.addData("offerId", Integer.toString(pushNotification.getOfferId()));
		final Message message = messageBuilder.build();
		
		for (AndroidRegisteredDevice device : devices) {
			counter++;
			partialDeviceRegistrationIds.add(device.getRegistrationId());
			int partialSize = partialDeviceRegistrationIds.size();
			if (partialSize == MULTICAST_SIZE || counter == total) {
				asyncSendToGCM(gcmExecutorService, exceptionList, sender, partialDeviceRegistrationIds, message, failCount);
				partialDeviceRegistrationIds.clear();
			}
		}

		gcmExecutorService.shutdown();
		try {
			boolean finished = gcmExecutorService.awaitTermination(100000, TimeUnit.MILLISECONDS);
			if (!finished) {
				LOG.warn("Not all threads have finished their execution!");
			}
		} catch (InterruptedException e) {
			LOG.error("Error ocured when awaiting termination of gcmExecutorService", e);
		}
		return exceptionList;
	}

	private void asyncSendToGCM(final ExecutorService gcmExecutorService, final List<Future<Exception>> exceptionList, final Sender sender, final List<String> partialDeviceRegistrationIds, final Message message, final AtomicInteger failCount) {
		// make a copy
		final List<String> devices = new ArrayList<String>(partialDeviceRegistrationIds);
		final Future<Exception> exception = gcmExecutorService.submit(new Callable<Exception>() {

			public Exception call() {
				try {

					MulticastResult multicastResult;
					try {
						multicastResult = sender.send(message, devices, 3);
					} catch (IOException e) {
						LOG.error("Error posting message. Devices: " + devices, e);
						failCount.addAndGet(devices.size());
						return e;
					} catch (IllegalArgumentException e) {
						LOG.error("Error posting message. Devices: " + devices, e);
						failCount.addAndGet(devices.size());
						return new InternalServerErrorException("IllegalArgumentException - server IP is probably not whitelisted");
					}
					List<Result> results = multicastResult.getResults();
					// analyze the results
					for (int i = 0; i < devices.size(); i++) {
						String regId = devices.get(i);
						Result result = results.get(i);
						String messageId = result.getMessageId();
						if (messageId != null) {
							String canonicalRegId = result.getCanonicalRegistrationId();
							if (canonicalRegId != null) {
								// same device has more than one registration
								// id:
								// update it
								androidRegisteredDeviceDAO.updateRegistration(regId, canonicalRegId);
							}
						} else {
							String error = result.getErrorCodeName();
							if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
								// application has been removed from device -
								// unregister it
								androidRegisteredDeviceDAO.unregister(regId);
							} else {
								LOG.error("Error sending message to " + regId + ": " + error);
								failCount.incrementAndGet();
							}
						}
					}
					return null;
				} catch (Exception e) {
					LOG.error("Unexpected exception in asyncSendToGCM: " + e.getLocalizedMessage() + "\nDevices: " + devices, e);
					failCount.addAndGet(devices.size());
					return e;
				}
			}
		});
		exceptionList.add(exception);
	}

}
