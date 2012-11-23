package mgmtClient;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

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
				AuctionEvent auctionEvent = (AuctionEvent) event;
				String message = buildMessage(auctionEvent.getType(), timeStampToString(auctionEvent.getTimeStamp()), "Auction with ID " + auctionEvent.getAuctionID() + " started");
				managementClient.inbox(message);
			}
			else if(event.getType().equals("AUCTION_ENDED")) {
				AuctionEvent auctionEvent = (AuctionEvent) event;
				String message = buildMessage(auctionEvent.getType(), timeStampToString(auctionEvent.getTimeStamp()), "Auction with ID " + auctionEvent.getAuctionID() + " ended");
				managementClient.inbox(message);
			}
		}
		
		//BidEvent
		else if(event instanceof BidEvent) {
			if(event.getType().equals("BID_PLACED")) {
				BidEvent bidEvent = (BidEvent) event;
				String message = buildMessage(bidEvent.getType(), timeStampToString(bidEvent.getTimeStamp()), "User " + bidEvent.getUserName() + " placed bid " + bidEvent.getPrice() + " on Auction " + bidEvent.getAuctionID());
				managementClient.inbox(message);
			}
			else if(event.getType().equals("BID_OVERBID")) {
				BidEvent bidEvent = (BidEvent) event;
				String message = buildMessage(bidEvent.getType(), timeStampToString(bidEvent.getTimeStamp()), "User " + bidEvent.getUserName() + "has been overbid on Auction " + bidEvent.getAuctionID());
				managementClient.inbox(message);
			}
			else if(event.getType().equals("BID_WON")) {
				BidEvent bidEvent = (BidEvent) event;
				String message = buildMessage(bidEvent.getType(), timeStampToString(bidEvent.getTimeStamp()), "User " + bidEvent.getUserName() + " won Auction " + bidEvent.getAuctionID() + " with " + bidEvent.getPrice());
				managementClient.inbox(message);
			}
		}
		
		//StatisticsEvent
		else if(event instanceof StatisticsEvent) {
			if(event.getType().equals("USER_SESSIONTIME_MIN")) {
				StatisticsEvent statisticsEvent = (StatisticsEvent) event;
				String message = buildMessage(statisticsEvent.getType(), timeStampToString(statisticsEvent.getTimeStamp()), "minimum session time is " + statisticsEvent.getValue() + " seconds");
				managementClient.inbox(message);
			}
			else if(event.getType().equals("USER_SESSIONTIME_MAX")) {
				StatisticsEvent statisticsEvent = (StatisticsEvent) event;
				String message = buildMessage(statisticsEvent.getType(), timeStampToString(statisticsEvent.getTimeStamp()), "maximum session time is " + statisticsEvent.getValue() + " seconds");
				managementClient.inbox(message);
			}
			else if(event.getType().equals("USER_SESSIONTIME_AVG")) {
				StatisticsEvent statisticsEvent = (StatisticsEvent) event;
				String message = buildMessage(statisticsEvent.getType(), timeStampToString(statisticsEvent.getTimeStamp()), "average session time is " + statisticsEvent.getValue() + " seconds");
				managementClient.inbox(message);
			}
			else if(event.getType().equals("BID_PRICE_MAX")) {
				StatisticsEvent statisticsEvent = (StatisticsEvent) event;
				String message = buildMessage(statisticsEvent.getType(), timeStampToString(statisticsEvent.getTimeStamp()), "maximum price seen so far is" + statisticsEvent.getValue());
				managementClient.inbox(message);
			}
			else if(event.getType().equals("BID_COUNT_PER_MINUTE")) {
				StatisticsEvent statisticsEvent = (StatisticsEvent) event;
				String message = buildMessage(statisticsEvent.getType(), timeStampToString(statisticsEvent.getTimeStamp()), "current bids per minute is " + statisticsEvent.getValue());
				managementClient.inbox(message);
			}
			else if(event.getType().equals("AUCTION_TIME_AVG")) {
				StatisticsEvent statisticsEvent = (StatisticsEvent) event;
				String message = buildMessage(statisticsEvent.getType(), timeStampToString(statisticsEvent.getTimeStamp()), "average auction time is " + statisticsEvent.getValue() + " seconds");
				managementClient.inbox(message);
			}
			else if(event.getType().equals("AUCTION_SUCCESS_RATIO")) {
				StatisticsEvent statisticsEvent = (StatisticsEvent) event;
				String message = buildMessage(statisticsEvent.getType(), timeStampToString(statisticsEvent.getTimeStamp()), "current auction success ration is " + statisticsEvent.getValue());
				managementClient.inbox(message);
			}
		}
		
		//UserEvent
		else if(event instanceof UserEvent) {
			if(event.getType().equals("USER_LOGIN")) {
				UserEvent userEvent = (UserEvent) event;
				String message = buildMessage(userEvent.getType(), timeStampToString(userEvent.getTimeStamp()), "user " + userEvent.getUserName() + " has logged in");
				managementClient.inbox(message);
			}
			else if(event.getType().equals("USER_LOGOUT")) {
				UserEvent userEvent = (UserEvent) event;
				String message = buildMessage(userEvent.getType(), timeStampToString(userEvent.getTimeStamp()), "user " + userEvent.getUserName() + " has logged out");
				managementClient.inbox(message);
			}
			else if(event.getType().equals("USER_DISCONNECTED")) {
				UserEvent userEvent = (UserEvent) event;
				String message = buildMessage(userEvent.getType(), timeStampToString(userEvent.getTimeStamp()), "user " + userEvent.getUserName() + " has disconnected");
				managementClient.inbox(message);
			}
		}
	}
	
	private String timeStampToString(long timeStamp) {
		String returnString = "";
		
		Date date = new Date();
		date.setTime(timeStamp);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		int day = calendar.get(Calendar.DATE);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		
		int hours = calendar.get(Calendar.HOUR);
		int minutes = calendar.get(Calendar.MINUTE);
		int seconds = calendar.get(Calendar.SECOND);
		
		String timezone = "CET";
		
		returnString = day + "." + month + "." + year + " " + hours + ":" + minutes + ":" + seconds + " " + timezone;
		
		return returnString;
	}
	
	private String buildMessage(String type, String time, String message) {
		String returnString = "";
		
		returnString += type + ": " + time + " - " + message;
		
		return returnString;
	}

}
