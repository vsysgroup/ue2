package billingServer;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * remote interface for login authenification
 * @author Babz
 *
 */
public interface IBillingServer extends Remote {

	/**
	 * 
	 * @param username
	 * @param password
	 * @return null if user not authorized, access otherwise
	 * @throws RemoteException
	 */
	IBillingServerSecure login(String username, String password) throws RemoteException;
}
