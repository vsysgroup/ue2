package analyticsServer;

public class StatisticsEvent extends Event {
	public double value = 0;

	public StatisticsEvent(String ID, String type, long timeStamp, double value) {
		super(ID, type, timeStamp);
		this.value = value;
	}
	
	public double getValue() {
		return value;
	}
}
