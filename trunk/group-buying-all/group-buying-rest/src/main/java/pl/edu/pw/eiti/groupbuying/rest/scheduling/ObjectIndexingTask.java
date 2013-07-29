package pl.edu.pw.eiti.groupbuying.rest.scheduling;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import pl.edu.pw.eiti.groupbuying.core.dao.CityDAO;
import pl.edu.pw.eiti.groupbuying.core.dao.OfferDAO;

/**
 * Task responsible for indexing data in a Lucene index
 * @author Piotr Zawadzki
 *
 */
@Component("objectIndexingTask")
public class ObjectIndexingTask{

	private static final Logger LOG = Logger.getLogger(ObjectIndexingTask.class);
	
	@Autowired
	private OfferDAO offerDAO;
	
	@Autowired
	private CityDAO cityDAO;

	/**
	 * Indexes available offers
	 */
	@Scheduled(initialDelay = 60000, fixedDelay = 300000) //index every 5 minutes
	public void indexOffers() {
		if(LOG.isDebugEnabled()) {
			LOG.debug("indexOffers START");
		}
		
		int offersIndexed = offerDAO.indexOffers();
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("indexOffers END - " + offersIndexed + " offers indexed.");
		}
	}
	
	/**
	 * Indexes enabled cities
	 */
	@Scheduled(initialDelay = 30000, fixedDelay = 900000) //index every 15 minutes
	public void indexCities() {
		if(LOG.isDebugEnabled()) {
			LOG.debug("indexCities START");
		}
		
		int citiesIndexed = cityDAO.indexCities();
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("indexCities END - " + citiesIndexed + " cities indexed.");
		}
	}

}
