package registry;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import org.apache.log4j.Logger;

/**
 * creates registry once;
 * @author Babz
 *
 */
public class RegistryCreator {

	public static final Logger LOG = Logger.getLogger(RegistryCreator.class);
	
	private static RegistryCreator instance = null;
	RegistryReader registryLocation = new RegistryReader();
	
	public static synchronized RegistryCreator getInstance() {
		if(instance == null) {
			instance = new RegistryCreator();
		}
		return instance;
	}
	
	private RegistryCreator() {
		try {
			LocateRegistry.createRegistry(registryLocation.getPort());
			LOG.info("registry created");
		} catch (RemoteException e) {
			LOG.info("couldnt create registry");
		}
	}
}