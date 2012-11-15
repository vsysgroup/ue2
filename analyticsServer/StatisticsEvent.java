package analyticsServer;

/**
 * This class represents a StatisticsEvent.
 * @author Philipp Pfeiffer 0809357
 *
 */
public class StatisticsEvent extends Event {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4443214497508813165L;
	public double value = 0;

	public StatisticsEvent(String ID, String type, long timeStamp, double value) {
		super(ID, type, timeStamp);
		this.value = value;
	}
	
	public double getValue() {
		return value;
	}
}
