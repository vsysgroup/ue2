package BillingServer;

/**
 * represent configuration of price steps
 * @author Babz
 *
 */
public class PriceSteps {

	public static PriceSteps instance = null;
	
	private PriceSteps() {}

	public static PriceSteps getInstance() {
		if(instance == null) {
			instance = new PriceSteps();
		}
		return instance;
	}
	
	
}
