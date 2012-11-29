package testing;

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
		
	}

	
}
