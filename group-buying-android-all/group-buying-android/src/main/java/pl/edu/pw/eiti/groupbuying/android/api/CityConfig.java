package pl.edu.pw.eiti.groupbuying.android.api;

import java.util.ArrayList;

public class CityConfig {

	private City myCity;
	private ArrayList<City> cities;

	public City getMyCity() {
		return myCity;
	}

	public void setMyCity(City myCity) {
		this.myCity = myCity;
	}

	public ArrayList<City> getCities() {
		return cities;
	}

	public void setCities(ArrayList<City> cities) {
		this.cities = cities;
	}

	@Override
	public String toString() {
		return "CityConfig [myCity=" + myCity + ", cities=" + cities + "]";
	}

}
