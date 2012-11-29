package testing;

import client.Client;

public class AuctionCreator implements Runnable {

	private static int auctionNo = 0;
	
	private int sleepDurationCreation;
	private int auctionDuration;
	private Client client;

	public AuctionCreator(Client client, int auctionDuration,
			int sleepDurationCreation) {
		this.client = client;
		this.auctionDuration = auctionDuration;
		this.sleepDurationCreation = sleepDurationCreation;
	}

	@Override
	public void run() {

		while(true) {
			client.createAuction(auctionDuration, "test" + ++auctionNo);

			try {
				Thread.sleep(sleepDurationCreation);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
