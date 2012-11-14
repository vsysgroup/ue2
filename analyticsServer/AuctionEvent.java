package analyticsServer;

public class AuctionEvent extends Event {
	private long auctionID = 0;

	public AuctionEvent(String ID, String type, long timeStamp, long auctionID) {
		super(ID, type, timeStamp);
		this.auctionID = auctionID;
		
	}
}
