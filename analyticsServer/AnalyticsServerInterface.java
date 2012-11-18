package analyticsServer;

import java.rmi.Remote;

/**
 * This interface provides methods available for remote calls.
 * @author Philipp Pfeiffer 0809357
 *
 */
public interface AnalyticsServerInterface extends Remote {
	
	public String subscribe(String filter, Notify n);
	
	public void processEvent(Event event);
	
	public void unsubscribe(String identifier);
	
}
