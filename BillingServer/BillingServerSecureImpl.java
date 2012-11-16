package billingServer;

import java.io.Serializable;

import org.apache.log4j.Logger;


public class BillingServerSecureImpl implements IBillingServerSecure, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final Logger LOG = Logger.getLogger(BillingServerSecureImpl.class);
	
	public BillingServerSecureImpl() {
		LOG.info("i was called YIHAA");
	}
	
	public PriceSteps getPriceSteps() {
//		TODO implement
//		return PriceSteps.getInstance();
		return null;
	}
	
	
	public void createPriceStep(double startPrice, double endPrice, double fixedPrice, double variablePricePercent) {
//		TODO implement
	}
	
	
	public void deletePriceStep(double startPrice, double endPrice) {
//		TODO implement
	}
	
	
	public void billAuction(String user, long auctionID, double price) {
//		TODO implement
	}
	
	
	public Bill getBill(String user) {
		//TODO implement
		return null;
	}
}
