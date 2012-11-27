package billingServer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


public class BillingServerSecureImpl implements IBillingServerSecure, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static BillingServerSecureImpl instance = null;
	public static final Logger LOG = Logger.getLogger(BillingServerSecureImpl.class);
	private PriceSteps priceSteps = PriceSteps.getInstance();
	private List<Bill> userBills = new ArrayList<Bill>();
	
	public static synchronized BillingServerSecureImpl getInstance() {
		if(instance == null) {
			instance = new BillingServerSecureImpl();
		}
		return instance;
	}
	
	private BillingServerSecureImpl() {
//		LOG.info("i was called YIHAA");
	}
	
	public PriceSteps getPriceSteps() throws RemoteException {
		return priceSteps;
	}
	
	
	public void createPriceStep(double startPrice, double endPrice, double fixedPrice, double variablePricePercent) {
		priceSteps.createStep(startPrice, endPrice, fixedPrice, variablePricePercent);
	}
	
	
	public void deletePriceStep(double startPrice, double endPrice) {
		priceSteps.deleteStep(startPrice, endPrice);
	}
	
	
	public void billAuction(String user, long auctionID, double price) {
		userBills.add(new Bill(user, auctionID, price));
	}
	
	
	public Bill getBill(String user) {
//		for(Bill b: userBills) {
//			if(user.equals(b.getUser())) {
//				
//			}
//		}
		return null;
	}
}
