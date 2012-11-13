package BillingServer;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

public class UserPropertyReader {
	
	public static UserPropertyReader instance = null;
	private Map<String, byte[]> permittedUser = new HashMap<String, byte[]>();

	public static synchronized UserPropertyReader getInstance() {
		if(instance == null) {
			instance = new UserPropertyReader(); 
		}
		return instance;
	}
	
	private UserPropertyReader() {
		InputStream is = ClassLoader.getSystemResourceAsStream("user.properties");
		if (is != null) {
			Properties props = new Properties();
			try {
				props.load(is);
				Set<String> userPW= new TreeSet<String>(props.stringPropertyNames());
				Iterator<String> it = userPW.iterator();
				while(it.hasNext()) {
					String[] tmp = it.next().split("\\s");
					permittedUser.put(tmp[0], tmp[1].getBytes());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			System.err.println("Properties file not found!");
		}
	}

	public Map<String, byte[]> getPermittedUser() {
		return permittedUser;
	}
}
