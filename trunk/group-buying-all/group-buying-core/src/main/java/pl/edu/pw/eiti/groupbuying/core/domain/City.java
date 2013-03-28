/*******************************************************************************
 * Copyright (c) 2013 Piotr Zawadzki.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Piotr Zawadzki - initial API and implementation
 ******************************************************************************/
package pl.edu.pw.eiti.groupbuying.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Latitude;
import org.hibernate.search.annotations.Longitude;
import org.hibernate.search.annotations.Spatial;

import pl.edu.pw.eiti.groupbuying.core.dto.CityDTO;

@Entity
@Indexed
@Spatial(name="loc")
@Table(name = "cities")
public class City {

	@Id
	@Column(name = "city_id")
	private String cityId;

	@Column(name = "name")
	private String name;
	
	@Latitude(of="loc")
	@Column(name = "latitude")
	private double latitude;
	
	@Longitude(of="loc")
	@Column(name = "longitude")
	private double longitude;
	
	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "City [cityId=" + cityId + ", name=" + name + "]";
	}
	
	public CityDTO getCityDTO() {
		CityDTO dto = new CityDTO(cityId, name, latitude, longitude);
		return dto;
	}

}
