package testing;

import server.Server;

public class AuctionListUpdater implements Runnable {

	private int updateInterval;
	private AuctionBidder auctionBidder;

	public AuctionListUpdater(AuctionBidder auctionBidder, int updateInterval) {
		this.auctionBidder = auctionBidder;
		this.updateInterval = updateInterval;
	}

	@Override
	public void run() {

		while (true) {
			auctionBidder.randomAuction = Server.getRandomAuction();
			try {
				Thread.sleep(updateInterval*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
