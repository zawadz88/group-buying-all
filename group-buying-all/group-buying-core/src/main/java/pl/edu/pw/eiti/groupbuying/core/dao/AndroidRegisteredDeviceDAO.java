package pl.edu.pw.eiti.groupbuying.core.dao;

import java.util.List;

import pl.edu.pw.eiti.groupbuying.core.domain.AndroidRegisteredDevice;


public interface AndroidRegisteredDeviceDAO {

	void register(AndroidRegisteredDevice device);

	void unregister(String registrationId);
	
	List<AndroidRegisteredDevice> getDevices();

	void updateRegistration(String registrationId, String canonicalRegistrationId);
}
