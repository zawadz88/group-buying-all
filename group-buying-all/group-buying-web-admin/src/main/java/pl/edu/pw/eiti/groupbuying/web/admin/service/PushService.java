package pl.edu.pw.eiti.groupbuying.web.admin.service;

import pl.edu.pw.eiti.groupbuying.web.admin.model.PushNotification;

/**
 * Service responsible for sending push notifications via GCM
 * @author Piotr Zawadzki
 *
 */
public interface PushService {
	/**
	 * Pushes a {@link PushNotification} to all registered Android devices
	 * @param pushNotification
	 */
	void push(PushNotification pushNotification);
}
