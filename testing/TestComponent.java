package testing;

import client.Client;
import exception.WrongParameterCountException;

/**
 * creates a new instance of a client and opens a createAuction thread as well as a bidOnAuction thread
 * @author Barbara Schwankl 0852176
 *
 */
public class TestComponent implements Runnable {

	private int auctionsPerMin;
	private int auctionDuration;
	private int updateInterval;
	private int bidsPerMin;
	
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
		String[] args = {"localhost", "13460" };
		Client client = null;
		try {
			client = new Client(args);
		} catch (WrongParameterCountException e) {
			System.out.println("ERROR: expected no of params: 2");
			e.printStackTrace();
		}
		
		int sleepDurationCreation;
		if(auctionsPerMin == 0) {
			sleepDurationCreation = 0;
		} else {
			sleepDurationCreation = 60000 / auctionsPerMin;
		}
		
		int sleepDurationBidding;
		if(bidsPerMin == 0) {
			sleepDurationBidding = 0;
		} else {
			sleepDurationBidding = 60000 /bidsPerMin;
		}
		
		Thread create = new Thread(new AuctionCreator(client, auctionDuration, sleepDurationCreation));
		Thread bid = new Thread(new AuctionBidder(client, updateInterval, sleepDurationBidding));
		create.start();
		bid.start();
		
	}

	
}
