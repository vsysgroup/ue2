package billingServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IBillingServerSecure extends Remote {

	/**
	 * returns current configuration of price steps
	 * TODO glaub da ist ein fehler in der angabe: diese methode sollte einen String zurückgeben
	 * @return price steps
	 */
	public PriceSteps getPriceSteps() throws RemoteException;
	
	/**
	 * allows to create a price step for a given price interval
	 * @param startPrice
	 * @param endPrice
	 * @param fixedPrice
	 * @param variablePricePercent
	 */
	public void createPriceStep(double startPrice, double endPrice, double fixedPrice, double variablePricePercent) throws RemoteException;
	
	/**
	 * allows to delete a price step for the pricing curve
	 * @param startPrice
	 * @param endPrice
	 */
	public void deletePriceStep(double startPrice, double endPrice) throws RemoteException;
	
	/**
	 * called by auction server as soon as auction has ended.
	 * stores the auction result.
	 * uses this information to calculate the bill for a user.
	 * @param user
	 * @param auctionID
	 * @param price
	 */
	public void billAuction(String user, long auctionID, double price) throws RemoteException;
	
	/**
	 * calculates and returns the bill for a given user, based on the price steps stored within the billing server.
	 * @param user
	 * @return
	 */
	public Bill getBill(String user) throws RemoteException;
	
}
