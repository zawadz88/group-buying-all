package pl.edu.pw.eiti.groupbuying.core;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import pl.edu.pw.eiti.groupbuying.core.dao.CityDAO;
import pl.edu.pw.eiti.groupbuying.core.dto.CityDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(value = {"group-buying-core.xml"})

public class SpatialTest {
	
	@Autowired
	private CityDAO cityDAO;
	
	@Autowired
	protected WebApplicationContext wac;

	protected MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void indexTest() {
		cityDAO.indexCities();
	}
	
	@Test
	public void closestCityTest() {
		CityDTO city = cityDAO.getClosestCity(50.0, 19.5);
		System.out.println("city: " + city);
	}
	
	@Test
	public void spatialControllerTest() throws Exception {
		this.mockMvc.perform(get("/cities/city")
				.accept(MediaType.APPLICATION_JSON)
				.param("latitude", "50")
				.param("longitude", "19")
				)
		.andDo(print());
	}
}
