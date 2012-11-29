package testing;

import client.Client;
import exception.WrongParameterCountException;

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
		String[] args = {"localhost", "13460" };
		Client client = null;
		try {
			client = new Client(args);
		} catch (WrongParameterCountException e) {
			System.out.println("ERROR: expected no of params: 2");
			e.printStackTrace();
		}
		int sleepDurationCreation = 60000 / auctionsPerMin;
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
