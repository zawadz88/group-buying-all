package pl.edu.pw.eiti.groupbuying.rest.controller;

import java.util.Date;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.edu.pw.eiti.groupbuying.core.domain.AndroidDeviceStatus;
import pl.edu.pw.eiti.groupbuying.core.domain.AndroidRegisteredDevice;
import pl.edu.pw.eiti.groupbuying.rest.exception.BadRequestException;
import pl.edu.pw.eiti.groupbuying.rest.exception.InternalServerErrorException;
import pl.edu.pw.eiti.groupbuying.rest.model.ApiError.ErrorCode;
import pl.edu.pw.eiti.groupbuying.rest.service.AndroidRegistrationService;

/**
 * Controller that registers/unregisters a device.
 * 
 * <p>
 * The client app should call this servlet everytime it receives a
 * {@code com.google.android.c2dm.intent.REGISTRATION C2DM} intent without an
 * error or {@code unregistered} extra.
 */
@Controller("androidRegistrationController")
@RequestMapping("/gcm")
public class AndroidRegistrationController {
	
	private static final Logger LOG = Logger.getLogger(AndroidRegistrationController.class);

	@Autowired
	private AndroidRegistrationService androidRegistrationService;
		
	@RequestMapping(value = "register", method = RequestMethod.POST)
	protected @ResponseBody boolean register(
			@RequestParam(value = "regId", required = false) final String registrationId,
			@RequestParam(value = "deviceName", required = false) String deviceName,
			@RequestParam(value = "systemInfo", required = false) String systemInfo) {
		validateRegistrationParams(registrationId, deviceName, systemInfo);
		
		AndroidRegisteredDevice device = new AndroidRegisteredDevice();
		device.setDeviceName(deviceName);
		device.setSystemInfo(systemInfo);
		device.setStatus(AndroidDeviceStatus.ACTIVE);
		device.setRegistrationId(registrationId);
		device.setTime(new Date());
		try {
			androidRegistrationService.registerDevice(device);
		} catch (DataAccessException e) {
			LOG.error("DB error occured in register, device: " + device, e);
			throw new InternalServerErrorException("Database error", ErrorCode.DATABASE_ERROR);
		} catch (PersistenceException e) {
			LOG.error("DB error occured in register, device: " + device, e);
			throw new InternalServerErrorException("Database error", ErrorCode.DATABASE_ERROR);
		} catch (Exception e) {
			LOG.error("Internal server error occured in register, device: " + device, e);
			throw new InternalServerErrorException("Unknown error", ErrorCode.UNKNOWN_ERROR);
		}
				
		return true;
	}
	
	private void validateRegistrationParams(final String registrationId, final String deviceName, final String systemInfo) {
		if(!StringUtils.hasText(registrationId)) {
			throw new BadRequestException("Parameter regID is empty or missing!", ErrorCode.MISSING_REGISTRATION_ID_PARAM);
		}
		if(!StringUtils.hasText(deviceName)) {
			throw new BadRequestException("Parameter devName is empty or missing!", ErrorCode.MISSING_DEVICE_NAME_PARAM);
		}
		if(!StringUtils.hasText(systemInfo)) {
			throw new BadRequestException("Parameter sysInfo is empty or missing!", ErrorCode.MISSING_SYSTEM_INFO_PARAM);
		}		
	}

}
