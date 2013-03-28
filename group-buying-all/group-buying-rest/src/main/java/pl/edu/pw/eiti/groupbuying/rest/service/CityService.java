package pl.edu.pw.eiti.groupbuying.rest.service;

import pl.edu.pw.eiti.groupbuying.core.dto.CityDTO;

public interface CityService {

	CityDTO getClosetCity(double latitude, double longitude);
}
