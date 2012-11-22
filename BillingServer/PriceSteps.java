package billingServer;

import java.util.Set;
import java.util.TreeSet;

/**
 * represents configuration of price steps
 * @author Babz
 *
 */
public class PriceSteps {

	private static PriceSteps instance = null;
	private Set<Step> allPriceSteps = new TreeSet<Step>();
	
	public static synchronized PriceSteps getInstance() {
		if(instance == null) {
			instance = new PriceSteps();
		}
		return instance;
	}
	
	private PriceSteps() {}
	
	public void createStep(double startPrice, double endPrice, double fixedPrice, double variablePricePercent) {
		Step newStep = new Step(startPrice, endPrice, fixedPrice, variablePricePercent);
		allPriceSteps.add(newStep);
	}
	
	public void deleteStep(double startPrice, double endPrice) {
		//TODO implement
	}
	
	public String toString() {
		//TODO iterate over set
		String stepRepresentation = "nothing to represent";
		return stepRepresentation;
	}
	
}
