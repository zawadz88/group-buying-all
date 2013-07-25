package pl.edu.pw.eiti.groupbuying.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * An entity containing info about a device registered for GCM push notifications
 * @author Piotr Zawadzki
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "android_registered_devices")
public class AndroidRegisteredDevice implements Serializable {
	
	/**
	 * Inner uinque identifier
	 */
	@Id
	@Column(name = "id")
	private long id;

	/**
	 * Device's registration ID obtained from GCM
	 */
	@Column(name = "registration_id", unique = true)
	private String registrationId;

	/**
	 * Make and model of the device
	 */
	@Column(name = "device_name")
	private String deviceName;
	
	/**
	 * Information about the system including e.g. OS version
	 */
	@Column(name = "system_info")
	private String systemInfo;

	/**
	 * Time of the last update of device's status
	 */
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name = "time")
	private Date time;
	
	/**
	 * GCM registration status 
	 */
	@Column(name = "status")
	@Enumerated(EnumType.ORDINAL)
	private AndroidDeviceStatus status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getSystemInfo() {
		return systemInfo;
	}

	public void setSystemInfo(String systemInfo) {
		this.systemInfo = systemInfo;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public AndroidDeviceStatus getStatus() {
		return status;
	}

	public void setStatus(AndroidDeviceStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "AndroidRegisteredDevice [id=" + id + ", registrationId=" + registrationId + ", deviceName=" + deviceName + ", systemInfo=" + systemInfo + ", time=" + time + ", status=" + status + "]";
	}
	
}
