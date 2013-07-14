package pl.edu.pw.eiti.groupbuying.core;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Random;

import org.apache.commons.codec.binary.Hex;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.edu.pw.eiti.groupbuying.core.dao.CityDAO;
import pl.edu.pw.eiti.groupbuying.core.dto.CityDTO;

@Ignore
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
	
	@Test
	public void messageDigestTest() throws Exception {
	MessageDigest messageDigest = MessageDigest.getInstance("MD5");
    messageDigest.reset();
	Random rand = new Random(System.currentTimeMillis());
	String randomString = Integer.toString(rand.nextInt(99999));
    messageDigest.update(randomString.getBytes(Charset.forName("UTF8")));
    byte[] digestedBytes = messageDigest.digest();
	String securityKey = new String(Hex.encodeHex(digestedBytes)).substring(0, 10);
	System.out.println("securityKey: " + securityKey);
	}
	
}
