package pl.edu.pw.eiti.groupbuying.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import pl.edu.pw.eiti.groupbuying.core.dao.CityDAO;
import pl.edu.pw.eiti.groupbuying.core.dto.CityDTO;
import pl.edu.pw.eiti.groupbuying.rest.service.CityService;

@Service("cityService")
public class CityServiceImpl implements CityService {

	@Autowired
	private CityDAO cityDAO;

	@Value("#{constants['city.latitude.max']}")
	private double maxLatitude;
	
	@Value("#{constants['city.latitude.min']}")
	private double minLatitude;
	
	@Value("#{constants['city.longitude.max']}")
	private double maxLongitude;
	
	@Value("#{constants['city.longitude.min']}")
	private double minLongitude;


	@Override
	public CityDTO getClosestCity(double latitude, double longitude) {		
		CityDTO city = null;
		if(inSearchBounds(latitude, longitude)) {
			city = cityDAO.getClosestCity(latitude, longitude);
		}	
		if(city == null) {
			city = cityDAO.getDefaultCity();
		}
		return city;
	}

	@Override
	public CityDTO getDefaultCity() {
		return cityDAO.getDefaultCity();
	}
	
	@Override
	public List<CityDTO> getCities(){
		return cityDAO.getCities();
	}
	
	private boolean inSearchBounds(double latitude, double longitude) {
		if(latitude < maxLatitude && latitude > minLatitude && longitude < maxLongitude && longitude > minLongitude) {
			return true;
		}
		return false;
	}
}
