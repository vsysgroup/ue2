package billingServer;

import java.util.Collections;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * represents configuration of price steps
 * @author Babz
 *
 */
public class PriceSteps {

	private static PriceSteps instance = null;
	private SortedSet<Step> allPriceSteps = Collections.synchronizedSortedSet(new TreeSet<Step>());

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

	public synchronized void deleteStep(double startPrice, double endPrice) {
		for(Step s: allPriceSteps) {
			if((s.getStartPrice() == startPrice) && s.getEndPrice() == endPrice) {
				allPriceSteps.remove(s);
				break;
			}
		}
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Min_Price	Max_Price	Fee_Fixed	Fee_Variable" + "\n");
		Iterator<Step> it = allPriceSteps.iterator();
		while(it.hasNext()) {
			buffer.append(it.next().toString() + "\n");
		}
		return buffer.toString();
	}

}
