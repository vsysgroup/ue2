package mgmtClient;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import billingServer.IBillingServer;
import billingServer.RegistryReader;


/**
 * management client connects to analytics server and billing server via rmi
 * @author Babz
 *
 */
public class ManagementClient {

	public static final Logger LOG = Logger.getLogger(ManagementClient.class);
	private static String bindingName = "BillingServer";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//init logger
		BasicConfigurator.configure();

		new ManagementClient();

		lookupRMI();
	}

	private static void lookupRMI() {
		RegistryReader registryLocation = new RegistryReader();
		IBillingServer login = null;
		try {
			Registry registry = LocateRegistry.getRegistry(registryLocation.getHost(), registryLocation.getPort());
			login = (IBillingServer) registry.lookup(bindingName);
			LOG.info("registry looked up");
		} catch (RemoteException e) {
			LOG.info("problem occurred trying to get registry");
		} catch (NotBoundException e) {
			LOG.info("this remote object doesnt exist / hasnt been bound");
		}
	}

}
