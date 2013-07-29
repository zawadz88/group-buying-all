package pl.edu.pw.eiti.groupbuying.rest.service;

import java.util.List;

import pl.edu.pw.eiti.groupbuying.core.dto.CityDTO;

/**
 * Service for city operations
 * @author Piotr Zawadzki
 *
 */
public interface CityService {

	/**
	 * Returns a city closest to a specified location, or the default city if none available
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	CityDTO getClosestCity(double latitude, double longitude);
	
	/**
	 * Returns the default city
	 * @return
	 */
	CityDTO getDefaultCity();

	/**
	 * Returns a list of all available cities
	 * @return
	 */
	List<CityDTO> getCities();
}
