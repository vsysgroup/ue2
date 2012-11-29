package testing;

import org.apache.log4j.Logger;

import server.Auction;
import server.Server;
import client.Client;

/**
 * test clients that makes a bid on the active auctions for a given number of times a minute
 * @author Barbara Schwankl 0852176
 *
 */
public class AuctionBidder implements Runnable {

	public static final Logger LOG = Logger.getLogger(AuctionBidder.class);
	
	private int sleepDurationBidding;
	@SuppressWarnings("unused")
	private int updateInterval;
	private Client client;
	private Auction randomAuction;

	public AuctionBidder(Client client, int updateInterval,
			int sleepDurationBidding) {
		this.client = client;
		this.updateInterval = updateInterval;
		this.sleepDurationBidding = sleepDurationBidding;
	}

	@Override
	public void run() {
		
		while(true) {
			
			@SuppressWarnings("unused")
			String currAuctions = Server.currentAuctionList;
			randomAuction = Server.getRandomAuction();
			int id = randomAuction.getID();
			double amount = calculateAmount();
			client.placeBid(id, amount);
			LOG.info("test bid placed: id = " + id + " amount = " + amount);
			
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
