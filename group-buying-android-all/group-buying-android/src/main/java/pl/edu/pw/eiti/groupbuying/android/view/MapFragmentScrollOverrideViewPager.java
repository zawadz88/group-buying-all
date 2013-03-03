/*******************************************************************************
 * Copyright (c) 2013 Piotr Zawadzki.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Piotr Zawadzki - initial API and implementation
 ******************************************************************************/
package pl.edu.pw.eiti.groupbuying.android.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;


public class MapFragmentScrollOverrideViewPager extends ViewPager {

	public MapFragmentScrollOverrideViewPager(Context context) {
		super(context);
	}

	public MapFragmentScrollOverrideViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
		if(v instanceof MapHoldingRelativeLayout) {
			return true;
		}
	    return super.canScroll(v, checkV, dx, x, y);
	}
}
