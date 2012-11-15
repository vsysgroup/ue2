package analyticsServer;

/**
 * This class represents a UserEvent
 * @author Philipp Pfeiffer 0809357
 *
 */
public class UserEvent extends Event {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7120840440772694551L;
	public String userName = "";

	public UserEvent(String ID, String type, long timeStamp, String userName) {
		super(ID, type, timeStamp);
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}
}
