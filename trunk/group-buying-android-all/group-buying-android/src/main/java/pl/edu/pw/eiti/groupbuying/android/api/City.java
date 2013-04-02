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
package pl.edu.pw.eiti.groupbuying.android.api;

import java.io.Serializable;


@SuppressWarnings("serial")
public class City implements Serializable {

	private String cityId;

	private String name;
	
	private double latitude;

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
		return "City [cityId=" + cityId + ", name=" + name + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}

	public City() {
	}

	public City(String cityId, String name, double latitude, double longitude) {
		super();
		this.cityId = cityId;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}

}
