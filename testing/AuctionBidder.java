package testing;

import client.Client;

public class AuctionBidder implements Runnable {

	private int sleepDurationBidding;
	private int updateInterval;
	private Client client;

	public AuctionBidder(Client client, int updateInterval,
			int sleepDurationBidding) {
		this.client = client;
		this.updateInterval = updateInterval;
		this.sleepDurationBidding = sleepDurationBidding;
	}

	@Override
	public void run() {
		
		while(true) {
//			client.placeBid(ID, amount);

			try {
				Thread.sleep(sleepDurationBidding);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
