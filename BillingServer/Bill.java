package billingServer;


/**
 * stores all billing lines of a given user
 * @author Babz
 *
 */
public class Bill {

	private String user;
	private long auctionID;
	private double price;

	public Bill(String user, long auctionID, double price) {
		this.user = user;
		this.auctionID = auctionID;
		this.price = price;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public long getAuctionID() {
		return auctionID;
	}

	public void setAuctionID(long auctionID) {
		this.auctionID = auctionID;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
