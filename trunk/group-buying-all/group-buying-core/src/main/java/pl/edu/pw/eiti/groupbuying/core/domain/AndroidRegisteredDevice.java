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

@SuppressWarnings("serial")
@Entity
@Table(name = "android_registered_devices")
public class AndroidRegisteredDevice implements Serializable {
	

	@Id
	@Column(name = "id")
	private long id;

	@Column(name = "registration_id", unique = true)
	private String registrationId;

	@Column(name = "device_name")
	private String deviceName;
	
	@Column(name = "system_info")
	private String systemInfo;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name = "time")
	private Date time;
	
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
