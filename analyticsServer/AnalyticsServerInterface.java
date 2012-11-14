package analyticsServer;

import java.rmi.Remote;

public interface AnalyticsServerInterface extends Remote {
	
	public String subscribe(String filter);
	
	public Event processEvent(Event event);
	
	public void unsubscribe(String identifier);
	
}
