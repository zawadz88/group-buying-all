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
package pl.edu.pw.eiti.groupbuying.core.dto;

import java.io.Serializable;

/**
 * A DTO for an entity representing one of cities for which there are offers available
 * @author Piotr Zawadzki
 *
 */
public class CityDTO implements Serializable {
	
	/**
	 * Unique identifier
	 */
	private String cityId;
	
	/**
	 * Displayable city name
	 */
	private String name;
	
	/**
	 * Latitude of the city centre
	 */
	private double latitude;
	
	/**
	 * Longitude of the city centre
	 */
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
		return "CityDTO [cityId=" + cityId + ", name=" + name + "]";
	}

	public CityDTO() {
	}

	public CityDTO(String cityId, String name, double latitude, double longitude) {
		super();
		this.cityId = cityId;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}

}
