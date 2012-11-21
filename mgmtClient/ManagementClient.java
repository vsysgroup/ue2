package mgmtClient;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import registry.RegistryReader;

import billingServer.IBillingServer;
import billingServer.IBillingServerSecure;


/**
 * management client connects to analytics server and billing server via rmi
 * @author Babz
 *
 */
public class ManagementClient {

	public static final Logger LOG = Logger.getLogger(ManagementClient.class);
	private static String bindingName = "BillingServer";
	
	private IBillingServer loginHandler = null;
	private IBillingServerSecure auctionDetails = null;
	private Scanner in = new Scanner(System.in);
	
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
		
		String[] cmd;
		while(in.hasNext()) {
			cmd = in.nextLine().split("\\s");
			if(cmd[0].equals("!login")) {
				String username = cmd[1];
				String pw = cmd[2];
				try {
					auctionDetails = loginHandler.login(username, pw);
					LOG.info("mgmt client logged in");
				} catch (RemoteException e) {
					LOG.info("remote login failed");
				}
			} else {
				System.out.println("command unknown");
			}
		}
	}

	private void lookupRMI() {
		RegistryReader registryLocation = new RegistryReader();
		
		try {
			Registry registry = LocateRegistry.getRegistry(registryLocation.getHost(), registryLocation.getPort());
			loginHandler = (IBillingServer) registry.lookup(bindingName);
			LOG.info("registry looked up");
		} catch (RemoteException e) {
			LOG.info("problem occurred trying to get registry");
		} catch (NotBoundException e) {
			LOG.info("this remote object doesnt exist / hasnt been bound");
		}
	}

}
