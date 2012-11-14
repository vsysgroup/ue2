package analyticsServer;

import java.io.Serializable;

public abstract class Event implements Serializable {

	/**
	 * generated serial ID
	 */
	private static final long serialVersionUID = -7156523106236168052L;
	public String ID = "";
	public String type = "";
	public long timeStamp = 0;
	
	public Event(String ID, String type, long timeStamp) {
		this.ID = ID;
		this.type = type;
		this.timeStamp = timeStamp;
	}
	
	public String getID() {
		return ID;
	}
	
	public String getType() {
		return type;
	}
	
	public long getTimeStamp() {
		return timeStamp;
	}
}
