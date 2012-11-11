package server;

import java.util.ArrayList;
import java.util.Date;

/**
 * This thread checks the auctions for their expiration date every 1s and ends any auction that is past it's expiration date.
 * @author Philipp Pfeiffer 0809357
 *
 */
public class AuctionCheckThread extends Thread {
	
	private Server server;
	
	public AuctionCheckThread(Server server) {
		this.server = server;
	}
	
	public void run() {
		while(!interrupted() && server.getServerStatus()) {
			ArrayList<Auction> auctions = server.getAuctions();
			Date date = new Date();
			for(int i = 0; i < auctions.size(); i++) {
				Auction currentAuction = auctions.get(i);
				if(currentAuction.getEndDate().before(date)) {
					server.auctionEnded(currentAuction);
					auctions.remove(i);
				}
			}
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				//shuts down without error message
			}
		}
	}
	
	public void exit() {
		interrupt();
	}

}
