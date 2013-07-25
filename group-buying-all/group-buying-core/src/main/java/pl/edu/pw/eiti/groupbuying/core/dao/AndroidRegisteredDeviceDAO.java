package pl.edu.pw.eiti.groupbuying.core.dao;

import java.util.List;

import pl.edu.pw.eiti.groupbuying.core.domain.AndroidRegisteredDevice;

/**
 * DAO for operations on {@link AndroidRegisteredDevice} entities
 * @author Piotr Zawadzki
 *
 */
public interface AndroidRegisteredDeviceDAO {

	/**
	 * Registers a device
	 * @param device
	 */
	void register(AndroidRegisteredDevice device);

	/**
	 * Unregisters a device
	 * @param registrationId
	 */
	void unregister(String registrationId);
	
	/**
	 * Gets all the persisted devices
	 * @return
	 */
	List<AndroidRegisteredDevice> getDevices();

	/**
	 * Updates the registration ID of the device
	 * @param registrationId old registration ID
	 * @param canonicalRegistrationId new registration ID
	 */
	void updateRegistration(String registrationId, String canonicalRegistrationId);
}
