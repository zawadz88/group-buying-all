package pl.edu.pw.eiti.groupbuying.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import pl.edu.pw.eiti.groupbuying.core.dao.AndroidRegisteredDeviceDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.AndroidRegisteredDevice;
import pl.edu.pw.eiti.groupbuying.rest.service.AndroidRegistrationService;

@Service("androidRegistrationService")
public class AndroidRegistrationServiceImpl implements AndroidRegistrationService {

	@Autowired
	private AndroidRegisteredDeviceDAO androidRegisteredDeviceDAO;
	
	@Override
	public void registerDevice(final AndroidRegisteredDevice device) {
		try {
			androidRegisteredDeviceDAO.register(device);
		} catch (DataIntegrityViolationException e) {
			androidRegisteredDeviceDAO.unregister(device.getRegistrationId());
			androidRegisteredDeviceDAO.register(device);
		}
	}


}
