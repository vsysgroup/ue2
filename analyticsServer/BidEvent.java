package analyticsServer;

/**
 * This class represents a BidEvent.
 * @author Philipp Pfeiffer 0809357
 *
 */
public class BidEvent extends Event {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5770061771124680939L;
	public String userName = "";
	public long auctionID = 0;
	public double price = 0;

	public BidEvent(String ID, String type, long timeStamp, String userName, long auctionID, double price) {
		super(ID, type, timeStamp);
		this.userName = userName;
		this.auctionID = auctionID;
		this.price = price;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public long getAuctionID() {
		return auctionID;
	}
	
	public double getPrice() {
		return price;
	}
}
