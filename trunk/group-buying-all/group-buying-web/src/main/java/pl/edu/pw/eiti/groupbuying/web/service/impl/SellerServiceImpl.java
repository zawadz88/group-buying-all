package pl.edu.pw.eiti.groupbuying.web.service.impl;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;

import pl.edu.pw.eiti.groupbuying.core.dao.SellerDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.Seller;
import pl.edu.pw.eiti.groupbuying.web.service.SellerService;

@Service
public class SellerServiceImpl implements SellerService{

	@Autowired
	private SellerDAO sellerDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public boolean saveSeller(Seller seller) {
		System.out.println("Saving seller: " + seller.toString());
		encodeSeller(seller);
		sellerDAO.saveSeller(seller);
		System.out.println("Seller saved");
		return true;
	}

	private void encodeSeller(Seller seller) {
		Random r = new Random();
		
		String salt = Double.toString(r.nextDouble() * 1000000000);
		String encodedPassword = passwordEncoder.encodePassword(seller.getPassword(), salt);
		seller.setPassword(encodedPassword);
		seller.setSalt(salt);
		
	}

}