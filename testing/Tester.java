package testing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class Tester {

	private final static ExecutorService threadpool = Executors.newCachedThreadPool();

	public static void main(String[] args) {

		//Management
		//eventSubscription on any event type (filter ".*")
		//auto mode

		LoadtestReader testParameters = new LoadtestReader();
		int noOfClients = testParameters.getNoOfClients();
		int auctionsPerMin = testParameters.getAuctionsPerMin();
		int auctionDuration = testParameters.getAuctionDuration();
		int updateIntervalSec = testParameters.getUpdateIntervalSec();
		int bidsPerMin = testParameters.getBidsPerMin();

		for(int i = 0; i <= noOfClients; ++i) {

			threadpool.execute(new TestComponent());
		}
	}

}
