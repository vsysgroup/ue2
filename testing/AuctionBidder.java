package testing;

import org.apache.log4j.Logger;

import server.Auction;
import client.Client;

/**
 * test clients that makes a bid on the active auctions for a given number of times a minute
 * @author Barbara Schwankl 0852176
 *
 */
public class AuctionBidder implements Runnable {

	public static final Logger LOG = Logger.getLogger(AuctionBidder.class);

	private int sleepDurationBidding;
	private Client client;
	private Auction randomAuction;

	public AuctionBidder(Client client, int sleepDurationBidding, Auction randomAuction) {
		this.client = client;
		this.sleepDurationBidding = sleepDurationBidding;
		this.randomAuction = randomAuction;
	}

	@Override
	public void run() {

		while(true) {
			
			if(randomAuction != null) {
				int id = randomAuction.getID();
				double amount = calculateAmount();
				client.placeBid(id, amount);
				LOG.info("test bid placed: id = " + id + " amount = " + amount);
			}

			try {
				Thread.sleep(sleepDurationBidding);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private double calculateAmount() {
		long currTime = System.currentTimeMillis();
		long auctionBeginTime = randomAuction.getCreationTime();
		return 10 + (currTime - auctionBeginTime);
	}

}
