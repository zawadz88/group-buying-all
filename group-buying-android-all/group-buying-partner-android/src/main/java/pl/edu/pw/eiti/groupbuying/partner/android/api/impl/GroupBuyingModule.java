
package pl.edu.pw.eiti.groupbuying.partner.android.api.impl;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.module.SimpleModule;

import pl.edu.pw.eiti.groupbuying.partner.android.api.CouponInfo;

public class GroupBuyingModule extends SimpleModule {
	
	public GroupBuyingModule() {
		super("GroupBuyingModule", new Version(1, 0, 0, null));
	}
	
	@Override
	public void setupModule(SetupContext context) {
		context.setMixInAnnotations(CouponInfo.class, CouponMixin.class);
	}
}
