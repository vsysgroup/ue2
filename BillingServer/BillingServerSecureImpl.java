package billingServer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;


public class BillingServerSecureImpl implements IBillingServerSecure, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static BillingServerSecureImpl instance = null;
	public static final Logger LOG = Logger.getLogger(BillingServerSecureImpl.class);
	private PriceSteps priceSteps = PriceSteps.getInstance();
	private Map<String, Bill> userBills = new HashMap<String, Bill>();
	
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
		Bill bill = userBills.get(user);
		if(bill == null) {
			bill = new Bill();
			bill.setUser(user);
			userBills.put(user, bill);
		}
		bill.addBillEntry(auctionID, price);		
	}
	
	
	public Bill getBill(String user) {
		Bill bill = userBills.get(user);
		bill.compute(priceSteps);
		return bill;
	}
}
