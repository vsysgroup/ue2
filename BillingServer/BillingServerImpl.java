package billingServer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Map.Entry;


/**
 * Implementation of Interface Billing Server
 * @author Babz
 *
 */
public class BillingServerImpl implements IBillingServer, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public IBillingServerSecure login(String username, String password) throws RemoteException {
		if(!checkAuthentification(username, password)) {
			return null;
		}
		//export remote
		BillingServerSecureImpl billingServerAccess = BillingServerSecureImpl.getInstance(); 
		UnicastRemoteObject.exportObject(billingServerAccess, 0);
		return billingServerAccess;
	}
	
	private boolean checkAuthentification(String name, String pw) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("no such algorithm");
		}
		
		byte[] pwHash = md.digest(pw.getBytes());
		Map<String, byte[]> user = UserPropertyReader.getInstance().getPermittedUser();
		
		for(Entry <String, byte[]> entry: user.entrySet()) {
			if(entry.getKey().equals(name)) {
				if(entry.getValue().equals(pwHash)) {
					return true;
				}
			}
		}
		return false;
	}
	
	//create hash for static userpasswords; never used by program
	@SuppressWarnings("unused")
	private void getPasswordHash() {
		String password = "alice123";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hash = md.digest(password.getBytes());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
