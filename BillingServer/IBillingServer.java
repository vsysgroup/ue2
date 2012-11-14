package BillingServer;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * remote interface for login authenification
 * @author Babz
 *
 */
public interface IBillingServer extends Remote {

	BillingServerSecureImpl login(String username, String password) throws RemoteException;
}
