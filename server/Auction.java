package server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Represents an auction
 * @author Philipp Pfeiffer 0809357
 *
 */
public class Auction {

	private static int newID = 0;
	private final int ID;
	
	private Date endDate;
	private User owner;
	private String description;
	private ArrayList<User> bidders = new ArrayList<User>();
	public double highestBid = 0;
	public User highestBidder = null;
	public Server server = null;
	
	public Auction(Date endDate, User owner, String description, Server server) {
		
		this.ID = newID++;
		this.endDate = endDate;
		this.owner = owner;
		this.description = description;
		this.server = server;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date date) {
		this.endDate = date;
	}
	
	public User getOwner() {
		return owner;
	}
	
	public void setOwner(User newOwner) {
		this.owner = newOwner;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getID() {
		return this.ID;
	}
	
	synchronized public void newBid(User bidder, double amount) {
		if(!bidders.contains(bidder)) {
			bidders.add(bidder);
			if(amount > highestBid) {
				highestBid = amount;
				User previousHighest = highestBidder;
				highestBidder = bidder;
				server.bidSuccessful(bidder, amount, description);
				if(previousHighest != null) {
					server.userOverbid(previousHighest, description);
				}
				
			}
			else {
				server.bidUnsuccessful(bidder, amount, highestBid, description);
			}
		}
		else {
			if(amount > highestBid) {
				highestBid = amount;
				User previousHighest = highestBidder;
				highestBidder = bidder;
				if(previousHighest != null) {
					server.userOverbid(previousHighest, description);
				}
			}
			else{
				server.bidUnsuccessful(bidder, amount, highestBid, description);
			}
			
		}
	}

	public ArrayList<User> getBidders() {
		return bidders;
	}

	public User getWinner() {
		return highestBidder;
	}
	
	public double getWinningBid() {
		return highestBid;
	}
	
	/**
	 * Brings the date into a String of the form: <date> <time> <timezone>
	 * @return
	 */
	public String dateToString() {
		String returnString = "";
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getEndDate());
		
		int day = calendar.get(Calendar.DATE);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		
		int hours = calendar.get(Calendar.HOUR);
		int minutes = calendar.get(Calendar.MINUTE);
		
		String timezone = "CET";
		
		returnString = day + "." + month + "." + year + " " + hours + ":" + minutes + " " + timezone;
		
		return returnString;
	}
	
}
