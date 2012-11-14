package analyticsServer;

public class UserEvent extends Event {
	public String userName = "";

	public UserEvent(String ID, String type, long timeStamp, String userName) {
		super(ID, type, timeStamp);
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}
}
