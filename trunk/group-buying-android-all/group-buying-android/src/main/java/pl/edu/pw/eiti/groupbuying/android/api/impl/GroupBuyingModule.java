
package pl.edu.pw.eiti.groupbuying.android.api.impl;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.module.SimpleModule;

import pl.edu.pw.eiti.groupbuying.android.api.Address;
import pl.edu.pw.eiti.groupbuying.android.api.City;
import pl.edu.pw.eiti.groupbuying.android.api.CityConfig;
import pl.edu.pw.eiti.groupbuying.android.api.Coupon;
import pl.edu.pw.eiti.groupbuying.android.api.Offer;
import pl.edu.pw.eiti.groupbuying.android.api.OfferEssential;
import pl.edu.pw.eiti.groupbuying.android.api.Seller;

public class GroupBuyingModule extends SimpleModule {
	
	public GroupBuyingModule() {
		super("GroupBuyingModule", new Version(1, 0, 0, null));
	}
	
	@Override
	public void setupModule(SetupContext context) {
		context.setMixInAnnotations(Address.class, AddressMixin.class);
		context.setMixInAnnotations(Offer.class, OfferMixin.class);
		context.setMixInAnnotations(City.class, CityMixin.class);
		context.setMixInAnnotations(CityConfig.class, CityConfigMixin.class);
		context.setMixInAnnotations(Seller.class, SellerMixin.class);
		context.setMixInAnnotations(OfferEssential.class, OfferEssentialMixin.class);
		context.setMixInAnnotations(Coupon.class, CouponMixin.class);
	}
}
