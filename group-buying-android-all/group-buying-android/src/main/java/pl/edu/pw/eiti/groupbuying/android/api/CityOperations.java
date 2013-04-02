package pl.edu.pw.eiti.groupbuying.android.api;

import java.util.ArrayList;

public interface CityOperations {

	City getDefaultCity();
	City getNearestCity(double latitude, double longitude);
	ArrayList<City> getCities();	
	
}
