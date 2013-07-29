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
package pl.edu.pw.eiti.groupbuying.web.service;

import pl.edu.pw.eiti.groupbuying.core.domain.Seller;

/**
 * Service for seller operations
 * @author Piotr Zawadzki
 *
 */
public interface SellerService {
	/**
	 * Persists a seller
	 * @param seller
	 * @return
	 */
	public boolean saveSeller(Seller seller);
}
