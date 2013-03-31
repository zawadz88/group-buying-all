package pl.edu.pw.eiti.groupbuying.rest.service;

import pl.edu.pw.eiti.groupbuying.core.dto.CityDTO;

public interface CityService {

	CityDTO getClosestCity(double latitude, double longitude);
	
	CityDTO getDefaultCity();
}
