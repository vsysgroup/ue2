package billingServer;

import java.io.Serializable;

import org.apache.log4j.Logger;


public class BillingServerSecureImpl implements IBillingServerSecure, Serializable {
	
	public PriceSteps priceSteps = PriceSteps.getInstance();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final Logger LOG = Logger.getLogger(BillingServerSecureImpl.class);
	
	public BillingServerSecureImpl() {
		LOG.info("i was called YIHAA");
	}
	
	public PriceSteps getPriceSteps() {
		return priceSteps;
	}
	
	
	public void createPriceStep(double startPrice, double endPrice, double fixedPrice, double variablePricePercent) {
		priceSteps.createStep(startPrice, endPrice, fixedPrice, variablePricePercent);
	}
	
	
	public void deletePriceStep(double startPrice, double endPrice) {
		priceSteps.deleteStep(startPrice, endPrice);
	}
	
	
	public void billAuction(String user, long auctionID, double price) {
//		TODO implement
	}
	
	
	public Bill getBill(String user) {
		//TODO implement
		return null;
	}
}
