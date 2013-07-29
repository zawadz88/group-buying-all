package pl.edu.pw.eiti.groupbuying.rest.service;

import pl.edu.pw.eiti.groupbuying.core.domain.AndroidRegisteredDevice;

/**
 * Service for registering Android devices for GCM push notifications
 * @author Piotr Zawadzki
 *
 */
public interface AndroidRegistrationService {

	/**
	 * Registers a device
	 * @param device
	 */
	void registerDevice(AndroidRegisteredDevice device);
	
}
