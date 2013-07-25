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
package pl.edu.pw.eiti.groupbuying.core.dao;

import java.util.List;

import pl.edu.pw.eiti.groupbuying.core.dto.CityDTO;

/**
 * DAO for operations on {@link CityDTO} objects
 * @author Piotr Zawadzki
 *
 */
public interface CityDAO {

	/**
	 * Gets a list of all enabled cities
	 * @return
	 */
	List<CityDTO> getCities();
	
	/**
	 * Gets a city closest to the specified location
	 * @param latitude latitude of the current location
	 * @param longitude longitude of the current location
	 * @return closest city or the default city if no city in range
	 */
	CityDTO getClosestCity(double latitude, double longitude);
	
	/**
	 * Indexes the cities for the search engine
	 * @return
	 */
	int indexCities();

	/**
	 * Gets the default city
	 * @return
	 */
	CityDTO getDefaultCity();

}
