package client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import exception.WrongParameterCountException;

public class TestClient implements Runnable {

	private final static ExecutorService threadpool = Executors.newCachedThreadPool();

	public static void main(String[] args) {
		LoadtestReader testParameters = new LoadtestReader();
		int noOfClients = testParameters.getNoOfClients();
		int auctionsPerMin = testParameters.getAuctionsPerMin();
		int auctionDuration = testParameters.getAuctionDuration();
		int updateIntervalSec = testParameters.getUpdateIntervalSec();
		int bidsPerMin = testParameters.getBidsPerMin();
//		try {
//			for(int i = 0; i <= noOfClients; ++i) {
//
//				threadpool.execute(new Client(args));
//			}
//		} catch (WrongParameterCountException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	/**
	 * thread for testing component
	 */
	@Override
	public void run() {
		//TODO generate clients that overbid each other
	}
}
