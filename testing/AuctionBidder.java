package testing;

import server.Auction;
import server.Server;
import client.Client;

public class AuctionBidder implements Runnable {

	private int sleepDurationBidding;
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
			String currAuctions = Server.currentAuctionList;
			randomAuction = Server.getRandomAuction();
			client.placeBid(randomAuction.getID(), calculateAmount());

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
