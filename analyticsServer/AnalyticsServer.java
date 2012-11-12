package analyticsServer;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;

public class AnalyticsServer extends UnicastRemoteObject implements AnalyticsServerInterface {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String registryHost = "";
	private static int registryPort = 0;
	private static String bindingName = "AnalyticsServer";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//First the registry properties need to be read from the registry.properties file via loadRegistry()
		try {
			loadRegistry();
		} catch (IOException e) {
			System.err.println("Could not read properties.");
		}
		
		//Then a registry is created at the port given by the properties file
		try {
			LocateRegistry.createRegistry(registryPort);
		} catch (RemoteException e) {
			System.err.println("Could not create registry.");
		}
		
		//Next the constructor of the analyticsServer is called and the object is bound to the registry
		try {
			AnalyticsServerInterface analyticsServer = new AnalyticsServer(args);
			LocateRegistry.getRegistry(registryHost, registryPort).bind(bindingName, analyticsServer);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		}

	}
	
	public AnalyticsServer(String[] args) throws NumberFormatException, RemoteException {
		//TODO: server needs to do things
	}
	
	/**
	 * This method is used to read the registry properties from the registry.properties file
	 * @throws IOException
	 */
	private static void loadRegistry() throws IOException {
		InputStream registryPropertiesFile = ClassLoader.getSystemResourceAsStream("registry.properties");
		if(registryPropertiesFile != null) {
			Properties registryProperties = new Properties();
			registryProperties.load(registryPropertiesFile);
			registryHost = registryProperties.getProperty("registry.host");
			registryPort = Integer.parseInt(registryProperties.getProperty("registry.port"));
			registryPropertiesFile.close();
		} 
	}

}
