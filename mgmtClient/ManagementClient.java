package mgmtClient;

import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import registry.RegistryReader;

import analyticsServer.AnalyticsServerInterface;
import analyticsServer.Notify;
import billingServer.IBillingServer;
import billingServer.IBillingServerSecure;


/**
 * management client connects to analytics server and billing server via rmi
 * @author Barbara Schwankl 0852176
 * @author Philipp Pfeiffer 0809357
 *
 */
public class ManagementClient {

	public static final Logger LOG = Logger.getLogger(ManagementClient.class);
	private static String bindingNameBilling = "BillingServer";
	private static String bindingNameAnalytics = "AnalyticsServer";

	private static IBillingServer loginHandler = null;
	private IBillingServerSecure billingHandler = null;
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
		try {
			UnicastRemoteObject.exportObject(notify, 0);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}

		String[] cmd;
		while(in.hasNext()) {
			cmd = in.nextLine().split("\\s");
			if(cmd[0].equals("!login")) {
				String username = cmd[1];
				String pw = cmd[2];
				try {
					billingHandler = loginHandler.login(username, pw);
					LOG.info("mgmt client logged in");
				} catch (RemoteException e) {
					LOG.error("remote login failed");
				}
			}
			else if(cmd[0].equals("!steps")) {
				try {
					System.out.println(billingHandler.getPriceSteps());
				} catch (RemoteException e) {
					LOG.error("couldnt get price steps");
				}
				LOG.info("listed price steps");
			}
			else if(cmd[0].equals("!addStep")) {
				if(cmd.length != 5) {
					System.out.println("Expected parameters: startPrice, endPrice, fixedPrice, variablePricePercent");
					LOG.error("Wrong parameters");
				} else {
					try {
						double startPrice = Double.parseDouble(cmd[1]);
						double endPrice = Double.parseDouble(cmd[2]);
						double fixedPrice = Double.parseDouble(cmd[3]);
						double variablePricePercent = Double.parseDouble(cmd[4]);
						billingHandler.createPriceStep(startPrice, endPrice, fixedPrice, variablePricePercent);
					} catch (RemoteException e) {
						LOG.error("create price steps failed");
					} catch (NumberFormatException e) {
						System.out.println("wrong number format - supposed to be double");
					}
				}
			}
			else if(cmd[0].equals("!removeStep")) {
				if(cmd.length != 3) {
					System.out.println("Expected parameters: startPrice, endPrice");
					LOG.error("Wrong parameters");
				} else {
					try {
						double startPrice = Double.parseDouble(cmd[1]);
						double endPrice = Double.parseDouble(cmd[2]);
						billingHandler.deletePriceStep(startPrice, endPrice);
					} catch (RemoteException e) {
						LOG.error("delete price step failed");
					} catch (NumberFormatException e) {
						System.out.println("wrong number format - supposed to be double");
					}
				}
			}
			else if(cmd[0].equals("!bill")) {
				if(cmd.length != 2) {
					System.out.println("Expected parameters: username");
					LOG.error("Wrong parameters");
				} else {
					String user = cmd[1];
					try {
						billingHandler.getBill(user);
					} catch (RemoteException e) {
						LOG.error("getting bill failed");
					}
				}
			}
			else if(cmd[0].equals("!logout")) {
				if(cmd.length != 1) {
					LOG.error("Wrong parameters");
				} else {
					//TODO call BillingServer for logout
				}
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
						e.printStackTrace();
					}

				}
			}
			else if(cmd[0].equals("!auto")) {
				automaticPrintingOn = true;
				LOG.info("Automatic printing is ON");
			}
			else if(cmd[0].equals("!hide")) {
				automaticPrintingOn = false;
				LOG.info("Automatic printing is OFF");
			}
			else if(cmd[0].equals("!print")) {
				printList();
			}
			else if(cmd[0].equals("!exit")) {
				try {
					UnicastRemoteObject.unexportObject(notify, true);
				} catch (NoSuchObjectException e) {
					e.printStackTrace();
				}
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

	private void printList() {
		for(int i = 0; i < storedMessages.size(); i++) {
			System.out.println(storedMessages.get(i));
		}
		storedMessages.clear();
	}

	private void storeMessage(String message) {
		storedMessages.add(message);
	}

	public void inbox(String message) {
		if(automaticPrintingOn) {
			System.out.println(message);
		}
		else {
			storeMessage(message);
		}	
	}
}
