package mgmtClient;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import registry.RegistryReader;

import analyticsServer.AnalyticsServerInterface;
import analyticsServer.Notify;
import billingServer.IBillingServer;


/**
 * management client connects to analytics server and billing server via rmi
 * @author Babz
 * @author Philipp Pfeiffer 0809357
 *
 */
public class ManagementClient {

	public static final Logger LOG = Logger.getLogger(ManagementClient.class);
	private static String bindingNameBilling = "BillingServer";
	private static String bindingNameAnalytics = "AnalyticsServer";
	
	private static IBillingServer loginHandler = null;
	private static AnalyticsServerInterface analyticsHandler = null;
	private Scanner in = new Scanner(System.in);
	
	private boolean automaticPrintingOn = false;
	
	private ArrayList<String> subscriptions = new ArrayList<String>();
	private ArrayList<String> storedMessages = new ArrayList<String>();
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//init logger
		BasicConfigurator.configure();

		new ManagementClient();
	}
	
	private ManagementClient() {
		LOG.info("Starting Management Client");
		
		lookupRMI();
		
		Notify notify = new NotificationChecker(this);
		
		String[] cmd;
		while(in.hasNext()) {
			cmd = in.nextLine().split("\\s");
			if(cmd[0].equals("!login")) {
				String username = cmd[1];
				String pw = cmd[2];
				try {
					loginHandler.login(username, pw);
					LOG.info("mgmt client logged in");
				} catch (RemoteException e) {
					LOG.info("remote login failed");
				}
			}
			else if(cmd[0].equals("!steps")) {
				//TODO implement
			}
			else if(cmd[0].equals("!addStep")) {
				//TODO implement
			}
			else if(cmd[0].equals("!removeStep")) {
				//TODO implement
			}
			else if(cmd[0].equals("!bill")) {
				//TODO implement
			}
			else if(cmd[0].equals("!logout")) {
				//TODO implement
			}
			else if(cmd[0].equals("!subscribe")) {
				if(cmd.length != 2) {
					LOG.error("Wrong parameters");
				} else {
					String filterRegex = cmd[1];
					String subscriptionID;
					try {
						subscriptionID = analyticsHandler.subscribe(filterRegex, notify);
						subscriptions.add(subscriptionID);
						LOG.info("Created subscription with ID " + subscriptionID + " for events using filter " + filterRegex);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			else if(cmd[0].equals("!unsubscribe")) {
				if(cmd.length != 2) {
					LOG.error("Wrong parameters");
				} else {
					String subscriptionID = cmd[1];
					try {
						analyticsHandler.unsubscribe(subscriptionID);
						subscriptions.remove(subscriptions.indexOf(subscriptionID));
						LOG.info("subscription " + subscriptionID + " terminated");
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			else if(cmd[0].equals("!auto")) {
				automaticPrintingOn = true;
			}
			else if(cmd[0].equals("!hide")) {
				automaticPrintingOn = false;
			}
			else if(cmd[0].equals("!print")) {
				printList();
			}
			else if(cmd[0].equals("!exit")) {
				break;
			}
			else {
				System.out.println("command unknown");
			}
		}
		LOG.info("Management Client shutting down");
	}

	private static void lookupRMI() {
		RegistryReader registryLocation = new RegistryReader();
		try {
			Registry registry = LocateRegistry.getRegistry(registryLocation.getHost(), registryLocation.getPort());
			try {
				loginHandler = (IBillingServer) registry.lookup(bindingNameBilling);
				LOG.info("BillingServer looked up");
			} catch (NotBoundException e) {
				LOG.info("this remote object doesnt exist / hasnt been bound - BillingServer");
			}
			try {
				analyticsHandler = (AnalyticsServerInterface) registry.lookup(bindingNameAnalytics);
				LOG.info("AnalyticsServer looked up");
			} catch (NotBoundException e) {
				LOG.info("this remote object doesnt exist / hasnt been bound - AnalyticsServer");
			}
		} catch (RemoteException e) {
			LOG.info("problem occurred trying to get registry");
		}
		
		
		
		
	}
	
	public boolean isAutomaticPrintingOn() {
		return automaticPrintingOn;
	}
	
	private void printList() {
		for(int i = 0; i < storedMessages.size(); i++) {
			LOG.info(storedMessages.get(i));
		}
	}
	
	public void storeMessage(String message) {
		storedMessages.add(message);
	}

}
