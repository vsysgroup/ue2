package billingServer;

import java.io.Serializable;


public class BillingServerSecureImpl implements IBillingServerSecure, Serializable {
	
	private static final long serialVersionUID = 1L;
	public static BillingServerSecureImpl instance = null;

	private BillingServerSecureImpl() {}
	
	public static BillingServerSecureImpl getInstance() {
		if(instance == null) {
			instance = new BillingServerSecureImpl();
		}
		return instance;
	}

	public PriceSteps getPriceSteps() {
//		TODO implement
		return PriceSteps.getInstance();
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
