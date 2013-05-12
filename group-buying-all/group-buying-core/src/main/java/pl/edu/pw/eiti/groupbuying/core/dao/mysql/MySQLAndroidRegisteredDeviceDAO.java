package pl.edu.pw.eiti.groupbuying.core.dao.mysql;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.pw.eiti.groupbuying.core.dao.AndroidRegisteredDeviceDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.AndroidDeviceStatus;
import pl.edu.pw.eiti.groupbuying.core.domain.AndroidRegisteredDevice;


@Repository("androidRegisteredDeviceDAO")
public class MySQLAndroidRegisteredDeviceDAO implements AndroidRegisteredDeviceDAO {
	private static final Logger LOG = Logger.getLogger(MySQLAndroidRegisteredDeviceDAO.class);

	private static final String ANDROID_REGISTERED_DEVICES_TABLE_NAME = "android_registered_devices";
	
	private static final String SQL_UPDATE_REGISTRATION_ID = "insert into " + ANDROID_REGISTERED_DEVICES_TABLE_NAME + " (id, application_id, registration_id, device_name, system_info, time, status, store_id) (select id, application_id, ? as registration_id, device_name, system_info, CURRENT_TIMESTAMP, status, store_id  from " + ANDROID_REGISTERED_DEVICES_TABLE_NAME + " where registration_id = ?) on duplicate key update status = " + AndroidDeviceStatus.ACTIVE.ordinal();
	private static final String SQL_DELETE_ANDROID_REGISTERED_DEVICE_BY_REGISTRATION_ID = "delete from " + ANDROID_REGISTERED_DEVICES_TABLE_NAME + " where registration_id = ?";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public void register(final AndroidRegisteredDevice device) {
		entityManager.persist(device);
	}

	@Override
	@Transactional
	public List<AndroidRegisteredDevice> getDevices() {
		TypedQuery<AndroidRegisteredDevice> query = entityManager.createQuery("select d from AndroidRegisteredDevice d where d.status = :status", AndroidRegisteredDevice.class);
		query.setParameter("status", AndroidDeviceStatus.ACTIVE);

		return query.getResultList();
	}

	@Transactional
	@Override
	public void updateRegistration(final String registrationId, final String canonicalRegistrationId) {
		try {
			jdbcTemplate.update(SQL_UPDATE_REGISTRATION_ID, new Object[] {canonicalRegistrationId, registrationId});
			jdbcTemplate.update(SQL_DELETE_ANDROID_REGISTERED_DEVICE_BY_REGISTRATION_ID, new Object[] {registrationId});
		} catch(DataAccessException ex) {
			LOG.error("Error when updating registration registrationId: " + registrationId + ", canonicalRegistrationId: " + canonicalRegistrationId, ex);
		}		
	}

	@Override
	@Transactional
	public void unregister(String registrationId) {
		jdbcTemplate.update(SQL_DELETE_ANDROID_REGISTERED_DEVICE_BY_REGISTRATION_ID, new Object[] {registrationId});
	}


}
