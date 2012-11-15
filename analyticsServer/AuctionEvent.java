package analyticsServer;

/**
 * This class represents an AuctionEvent.
 * @author Philipp Pfeiffer 0809357
 *
 */
public class AuctionEvent extends Event {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3861867348808830937L;
	private long auctionID = 0;

	public AuctionEvent(String ID, String type, long timeStamp, long auctionID) {
		super(ID, type, timeStamp);
		this.auctionID = auctionID;
		
	}

	public long getAuctionID() {
		return auctionID;
	}

}
