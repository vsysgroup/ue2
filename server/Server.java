package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import communication.TCPCommunication;

import exception.WrongParameterCountException;

/**
 * Represents a Server
 * @author Philipp Pfeiffer 0809357
 *
 */
public class Server {
	
	private int tcpPort;
	private boolean serverStatus = false;
	private ServerTCPListenerThread serverTCPListenerThread = null;
	private AuctionCheckThread auctionCheckThread = null;
	private TCPCommunication tcpCommunication = null;
	private ServerSocket serverSocket = null;
	private ArrayList<User> users = new ArrayList<User>();
	private ArrayList<Auction> auctions = new ArrayList<Auction>();
	private Scanner in = new Scanner(System.in);

	
	public static void main(String[] args) {
		try {
			new Server(args);
		} catch(NumberFormatException e) {
			howToUse();
		} catch(WrongParameterCountException e) {
			howToUse();
		} 
	}
	
	/**
	 * 
	 * @param args
	 * @throws WrongParameterCountException
	 */
	public Server(String[] args) throws WrongParameterCountException{
		if(args.length != 1) {
			throw new WrongParameterCountException();
		} else {
			this.tcpPort = Integer.parseInt(args[0]);
		}
		
		System.out.println("Starting Server.");
		
		try {
			serverSocket = new ServerSocket(tcpPort);
		} catch (IOException e) {
			System.out.println("Could not create socket!");
			exit();
		}
		
		
		
		serverStatus = true;
		
		serverTCPListenerThread = new ServerTCPListenerThread(serverSocket, this);
		serverTCPListenerThread.start();
		auctionCheckThread = new AuctionCheckThread(this);
		auctionCheckThread.start();
		
		while(serverStatus) {
			while(in.hasNextLine() && serverStatus) {
				exit();
				return;
			}
		}
		System.out.println("test");
	}
	
	/**
	 * Receives a message and a socket and processes the message
	 * @param message
	 * @param socket
	 */
	public void receiveMessage(String message, Socket socket) {
		InetAddress inetAddress = socket.getInetAddress();
				
		String[] input = message.split(" ");
		
		/**
		 * logs the user in
		 * sends the following responses:
		 * login successful <username>
		 * login failed
		 */
		if(input[0].equals("!login")) {
			String username = input[1];
			int port = Integer.parseInt(input[2]);
			
			if(!userKnown(username)){
				User newUser = new User(username, inetAddress, port);
				users.add(newUser);
				newUser.logIn();
				new UDPNotificationThread(inetAddress, port, "login successful" + " " + username, this).start();
			}
			else{
				User user = findUser(username);
				if(user.loggedIn()) {
					new UDPNotificationThread(inetAddress, port, "login failed", this).start();
				}
				else{
					user.logIn();
					if(user.getPort() != port) {
						user.setPort(port);
					}
					if(!user.getInetAddress().equals(inetAddress)) {
						user.setInetAddress(inetAddress);
					}
					new UDPNotificationThread(inetAddress, port, "login successful" + " " + username, this).start();
					if(user.getSavedMessages().size() > 0) {
						for(int i = 0; i < user.getSavedMessages().size(); i++) {
							new UDPNotificationThread(inetAddress, port, user.getSavedMessages().get(i), this).start();
						}
						user.clearMessages();
					}
				}
			}
		}
		
		/**
		 * logs the user out
		 * sends the following responses:
		 * logout successful <username>
		 */
		if(input[0].equals("!logout")) {
			String username = input[1];
			User user = findUser(username);
			int port = user.getPort();
			if(user.loggedIn()) {
				user.logOut();
				new UDPNotificationThread(inetAddress, port, "logout successful" + " " + username, this).start();
			}
		}
		
		/**
		 * creates a new Auction
		 * sends the following responses:
		 * create successful <ID> <date> <time> <timezone> <description> 
		 */
		if(input[0].equals("!create")) {
			String username = input[1];
			User user = findUser(username);
			int port = user.getPort();
			Long seconds = Long.parseLong(input[2]);
			String description = "";
			for(int i = 3; i < input.length; i++) {
				description += input[i];
				description += " ";
			}
			Auction newAuction = createAuction(user, seconds, description);
			String endDate = newAuction.dateToString();
			int ID = newAuction.getID();
			
			new UDPNotificationThread(inetAddress, port, "create successful" + " " + ID + " " + endDate + " " + description, this).start();
		}
		
		/**
		 * creates a list and sends it to the user
		 * sends the following responses:
		 * list <list>
		 */
		if(input[0].equals("!list")) {
			String username = input[1];
			User user = findUser(username);
			int port = user.getPort();
			
			InetAddress returnAddress = user.getInetAddress();
			
			String list = buildList();
			
			new UDPNotificationThread(returnAddress, port, "list" + " " + list, this).start();
		}
		
		/**
		 * Places a bid on an auction
		 */
		if(input[0].equals("!bid")) {
			String username = input[1];
			User user = findUser(username);
			int port = user.getPort();
			findAuctionByID(input[2]).newBid(user, Double.parseDouble(input[3]));
		}
	}
	
	/**
	 * Finds an auction from the auction list by it's ID
	 * @param IDString
	 * @return Auction
	 */
	private Auction findAuctionByID(String IDString) {
		int ID = Integer.parseInt(IDString);
		for(int i = 0; i < auctions.size(); i++) {
			if(auctions.get(i).getID() == ID) {
				return auctions.get(i);
			}
		}
		return null;
	}

	public static void howToUse() {
		System.out.println("Parameters incorrect. Correct syntax: java Server <tcpPort>");
	}
	/**
	 * returns the server Status
	 * @return boolean
	 */
	public boolean getServerStatus() {
		return serverStatus;
	}
	
	/**
	 * shuts down the server and frees all resources
	 */
	public void exit() {
		in.close();
		serverStatus = false;
		auctionCheckThread.exit();
		serverTCPListenerThread.exit();
		for(int i = 0; i < users.size(); i++) {
			users.get(i).logOut();
		}
		try {
			serverSocket.close();
		} catch (IOException e) {
			//closing socket for shutdown
		}
	}
	
	/**
	 * Finds out whether a user is known or not
	 * @param name
	 * @return boolean
	 */
	public boolean userKnown(String name){
		Iterator<User> iter = users.iterator();
		User currentUser = null;
		while(iter.hasNext()) {
			currentUser = iter.next();
			if(currentUser.getUsername().equals(name)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * finds and returns a user by name
	 * @param name
	 * @return User
	 */
	public User findUser(String name) {
		Iterator<User> iter = users.iterator();
		User currentUser = null;
		while(iter.hasNext()) {
			currentUser = iter.next();
			if(currentUser.getUsername().equals(name)){
				return currentUser;
			}
		}
		return currentUser;
	}

	/**
	 * if a bid was unsuccessful, the bidder will be informed
	 * @param bidder
	 * @param amountBid
	 * @param amountHighestBid
	 * @param description
	 */
	public void bidUnsuccessful(User bidder, double amountBid, double amountHighestBid, String description) {
		InetAddress inetAddress = bidder.getInetAddress();
		int port = bidder.getPort();
		new UDPNotificationThread(inetAddress, port, "bid" + " " + "unsuccessful" + " " + amountBid + " " + amountHighestBid + " " + description , this).start();
	}
	
	/**
	 * Creates a new auction and returns it
	 * @param user
	 * @param seconds
	 * @param description
	 * @return Auction
	 */
	synchronized public Auction createAuction(User user, Long seconds, String description) {
		Date date = new Date();
		date.setTime(date.getTime()+(seconds*1000));
		
		Auction auction = new Auction(date, user, description, this);
		auctions.add(auction);
		return auction;
	}

	/**
	 * returns all currently running auctions
	 * @return
	 */
	public ArrayList<Auction> getAuctions() {
		return auctions;
	}

	/**
	 * When an auction ends, this method sends out notifications to all Users who have taken part in the auction
	 * @param currentAuction
	 */
	public void auctionEnded(Auction currentAuction) {
		ArrayList<User> bidders = currentAuction.getBidders();
		User winner = currentAuction.getWinner();
		double winningBid = currentAuction.getWinningBid();
		String description = currentAuction.getDescription();
		for(int i = 0; i < bidders.size(); i++) {
			if(!bidders.get(i).loggedIn()) {
				if(bidders.get(i).getUsername().equals(winner.getUsername())) {
					bidders.get(i).addMessage("!auction-ended" + " " + "You"  + " " + winningBid  + " " + description);
				}
				else {
					bidders.get(i).addMessage("!auction-ended"  + " " + winner.getUsername()  + " " + winningBid + " " + description);
				}
			}
			else {
				if(bidders.get(i).getUsername().equals(winner.getUsername())) {
					new UDPNotificationThread(winner.getInetAddress(), winner.getPort(), "!auction-ended" + " " + "You"  + " " + winningBid  + " " + description, this).start();
				}
				else {
					new UDPNotificationThread(bidders.get(i).getInetAddress(), bidders.get(i).getPort(), "!auction-ended"  + " " + winner.getUsername()  + " " + winningBid + " " + description, this).start();
				}
			}
		}
	}
	
	/**
	 * builds a list of all auctions
	 * @return String
	 */
	public String buildList() {
		String list = "";
		
		ArrayList<Auction> auctions = getAuctions();
		for(int i = 0; i < auctions.size(); i++) {
			int ID = auctions.get(i).getID();
			String description = auctions.get(i).getDescription();
			description.trim();
			String owner = auctions.get(i).getOwner().getUsername();
			double highestBid = auctions.get(i).getWinningBid();
			User highestBidder = auctions.get(i).getWinner();
			String highestBidderName = "";
			String endDate = auctions.get(i).dateToString();
			
			if(highestBidder == null) {
				highestBidderName = "none";
			} else {
				highestBidderName = highestBidder.getUsername();
			}
			list += ID + ". " + "'" + description + "'" + " " + owner + " " + endDate + " " + highestBid + " " + highestBidderName;
			list += "\n";
		}
		return list;
	}

	/**
	 * if a bid was successful, this method will notify the bidder
	 * @param bidder
	 * @param amount
	 * @param description
	 */
	public void bidSuccessful(User bidder, double amount, String description) {
		InetAddress inetAddress = bidder.getInetAddress();
		int port = bidder.getPort();
		new UDPNotificationThread(inetAddress, port, "bid" + " " + "successful" + " " + amount + " "  + description , this).start();
	}
	
	/**
	 * This notification is sent out whenever a user has been overbid on an auction.
	 * @param bidder
	 * @param description
	 */
	public void userOverbid(User bidder, String description) {
		InetAddress inetAddress = bidder.getInetAddress();
		int port = bidder.getPort();
		if(!bidder.loggedIn()) {
			bidder.addMessage("!new-bid" + description);
		}
		else {
			new UDPNotificationThread(inetAddress, port, "!new-bid" + " " + description, this).start();
		}
	}
}
