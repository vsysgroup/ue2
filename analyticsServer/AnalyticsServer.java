package analyticsServer;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import registry.RegistryCreator;
import registry.RegistryReader;

/**
 * The main class of the AnalyticsServer. It fetches the registry, binds and exports the implementation of analyticsServer
 * (AnalyticsServerImpl) to the registry as a remote object.
 * @author Philipp Pfeiffer 0809357
 *
 */
public class AnalyticsServer {
	
	public static final Logger LOG = Logger.getLogger(AnalyticsServer.class);
	private static String bindingName = "AnalyticsServer";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		bindingName = args[0];
		
		BasicConfigurator.configure();
		
		new AnalyticsServer();
		
		setupRMI();
	}

	private static void setupRMI() {
		RegistryReader registryLocation = new RegistryReader();
		AnalyticsServerImpl analyticsServer = new AnalyticsServerImpl();
		try {
			RegistryCreator.getInstance();
			AnalyticsServerInterface stub = (AnalyticsServerInterface) UnicastRemoteObject.exportObject(analyticsServer, 0);
			Registry registry = LocateRegistry.getRegistry(registryLocation.getHost(), registryLocation.getPort());
			registry.bind(bindingName, stub);
			LOG.info("registry bound");
		} catch (RemoteException e) {
			LOG.info("error getting registry");
		} catch (AlreadyBoundException e) {
			LOG.info("object already bound");
		}
	
	}
	
	

}
