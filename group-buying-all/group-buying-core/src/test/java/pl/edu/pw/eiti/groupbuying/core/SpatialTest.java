package pl.edu.pw.eiti.groupbuying.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.edu.pw.eiti.groupbuying.core.dao.CityDAO;
import pl.edu.pw.eiti.groupbuying.core.dto.CityDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"group-buying-core.xml"})

public class SpatialTest {
	
	@Autowired
	private CityDAO cityDAO;

	@Test
	public void indexTest() {
		cityDAO.indexCities();
	}
	
	@Test
	public void closestCityTest() {
		CityDTO city = cityDAO.getClosestCity(50.0, 19.5);
		System.out.println("city: " + city);
	}
	
}
