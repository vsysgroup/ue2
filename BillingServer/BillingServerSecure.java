package BillingServer;

public class BillingServerSecure {
	
	public static BillingServerSecure instance = null;

	private BillingServerSecure() {}
	
	public static BillingServerSecure getInstance() {
		if(instance == null) {
			instance = new BillingServerSecure();
		}
		return instance;
	}

	//TODO all methods throw remote exceptions
	
	/**
	 * returns current configuration of price steps
	 * TODO glaub da ist ein fehler in der angabe: diese methode sollte einen String zurückgeben
	 * @return price steps
	 */
	public PriceSteps getPriceSteps() {
//		TODO implement
		return PriceSteps.getInstance();
	}
	
	/**
	 * allows to create a price step for a given price interval
	 * @param startPrice
	 * @param endPrice
	 * @param fixedPrice
	 * @param variablePricePercent
	 */
	public void createPriceStep(double startPrice, double endPrice, double fixedPrice, double variablePricePercent) {
//		TODO implement
	}
	
	/**
	 * allows to delete a price step for the pricing curve
	 * @param startPrice
	 * @param endPrice
	 */
	public void deletePriceStep(double startPrice, double endPrice) {
//		TODO implement
	}
	
	/**
	 * called by auction server as soon as auction has ended.
	 * stores the auction result.
	 * uses this information to calculate the bill for a user.
	 * @param user
	 * @param auctionID
	 * @param price
	 */
	public void billAuction(String user, long auctionID, double price) {
//		TODO implement
	}
	
	/**
	 * calculates and returns the bill for a given user, based on the price steps stored within the billing server.
	 * @param user
	 * @return
	 */
	public Bill getBill(String user) {
		//TODO implement
		return null;
	}
}
