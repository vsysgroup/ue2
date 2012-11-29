package testing;

import client.Client;

/**
 * 
 * @author Barbara Schwankl 0852176
 *
 */
public class TestComponent implements Runnable {

	private int auctionsPerMin;
	private int auctionDuration;
	private int updateInterval;
	private int bidsPerMin;
	
	private static int auctionNo = 0;

	public TestComponent(int auctionsPerMin, int auctionDuration,
			int updateIntervalSec, int bidsPerMin) {
		this.auctionsPerMin = auctionsPerMin;
		this.auctionDuration = auctionDuration;
		updateInterval = updateIntervalSec;
		this.bidsPerMin = bidsPerMin;
	}

	@Override
	public void run() {

		//auction creation
//		Client client = new Client();
//		client.createAuction(auctionDuration, "test" + ++auctionNo);
		
		
	}

	
}
