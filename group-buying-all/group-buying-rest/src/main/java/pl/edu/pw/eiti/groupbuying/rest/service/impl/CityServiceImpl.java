package pl.edu.pw.eiti.groupbuying.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.pw.eiti.groupbuying.core.dao.CityDAO;
import pl.edu.pw.eiti.groupbuying.core.dto.CityDTO;
import pl.edu.pw.eiti.groupbuying.rest.service.CityService;

@Service("cityService")
public class CityServiceImpl implements CityService {

	@Autowired
	private CityDAO cityDAO;

	@Override
	@Transactional
	public CityDTO getClosetCity(double latitude, double longitude) {
		return cityDAO.getClosestCity(latitude, longitude);
	}
}
