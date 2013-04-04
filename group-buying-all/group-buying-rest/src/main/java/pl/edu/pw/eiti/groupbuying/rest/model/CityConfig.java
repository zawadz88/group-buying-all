package pl.edu.pw.eiti.groupbuying.rest.model;

import java.util.List;

import pl.edu.pw.eiti.groupbuying.core.dto.CityDTO;

public class CityConfig {

	private CityDTO myCity;
	private List<CityDTO> cities;

	public CityDTO getMyCity() {
		return myCity;
	}

	public void setMyCity(CityDTO myCity) {
		this.myCity = myCity;
	}

	public List<CityDTO> getCities() {
		return cities;
	}

	public void setCities(List<CityDTO> cities) {
		this.cities = cities;
	}

	@Override
	public String toString() {
		return "CityConfig [myCity=" + myCity + ", cities=" + cities + "]";
	}

}
