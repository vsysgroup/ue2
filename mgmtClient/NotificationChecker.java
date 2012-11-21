package mgmtClient;

import java.io.Serializable;
import java.rmi.RemoteException;

import analyticsServer.AuctionEvent;
import analyticsServer.BidEvent;
import analyticsServer.Event;
import analyticsServer.Notify;
import analyticsServer.StatisticsEvent;
import analyticsServer.UserEvent;

/**
 * This class implements the Notify interface and acts as a callback object, used by the ManagementClient to receive and
 * process notifications from the AnalyticsServer.
 * @author Philipp Pfeiffer 0809357
 *
 */
public class NotificationChecker implements Notify, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ManagementClient managementClient = null;
	
	public NotificationChecker(ManagementClient managementClient) {
		this.managementClient = managementClient;
	}

	@Override
	public void notify(Event event) throws RemoteException {
		if(event instanceof AuctionEvent) {
			if(event.getType().equals("AUCTION_STARTED")) {
				//TODO Implement
			}
			else if(event.getType().equals("AUCTION_ENDED")) {
				//TODO Implement
			}
		}
		
		//BidEvent
		else if(event instanceof BidEvent) {
			if(event.getType().equals("BID_PLACED")) {
				//TODO Implement
			}
			else if(event.getType().equals("BID_OVERBID")) {
				//TODO Implement
			}
			else if(event.getType().equals("BID_WON")) {
				//TODO Implement
			}
		}
		
		//StatisticsEvent
		else if(event instanceof StatisticsEvent) {
			if(event.getType().equals("USER_SESSIONTIME_MIN")) {
				//TODO Implement
			}
			else if(event.getType().equals("USER_SESSIONTIME_MAX")) {
				//TODO Implement
			}
			else if(event.getType().equals("USER_SESSIONTIME_AVG")) {
				//TODO Implement
			}
			else if(event.getType().equals("BID_PRICE_MAX")) {
				//TODO Implement
			}
			else if(event.getType().equals("BID_COUNT_PER_MINUTE")) {
				//TODO Implement
			}
			else if(event.getType().equals("AUCTION_TIME_AVG")) {
				//TODO Implement
			}
			else if(event.getType().equals("AUCTION_SUCCESS_RATIO")) {
				//TODO Implement
			}
		}
		
		//UserEvent
		else if(event instanceof UserEvent) {
			if(event.getType().equals("USER_LOGIN")) {
				//TODO Implement
			}
			else if(event.getType().equals("USER_LOGOUT")) {
				//TODO Implement
			}
			else if(event.getType().equals("USER_DISCONNECTED")) {
				//TODO Implement
			}
		}
		
	}

}
