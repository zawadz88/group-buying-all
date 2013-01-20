package pl.edu.pw.eiti.groupbuying.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import pl.edu.pw.eiti.groupbuying.core.domain.Seller;
import pl.edu.pw.eiti.groupbuying.web.service.SellerService;


@Controller("addSellerController")
public class AddSellerController extends BaseController {

	@Autowired
	private SellerService sellerService;
	
	public void submitSeller(Seller seller) {
		sellerService.saveSeller(seller);
	}


}
