package billingServer;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * represents configuration of price steps
 * @author Babz
 *
 */
public class PriceSteps implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static PriceSteps instance = null;
	private Set<Step> allPriceSteps = Collections.synchronizedSet(new HashSet<Step>());

	public static synchronized PriceSteps getInstance() {
		if(instance == null) {
			instance = new PriceSteps();
		}
		return instance;
	}
	
	public Set<Step> getSteps() { return allPriceSteps; }

	private PriceSteps() {}

	public void createStep(double startPrice, double endPrice, double fixedPrice, double variablePricePercent) {
		Step newStep = new Step(startPrice, endPrice, fixedPrice, variablePricePercent);
		allPriceSteps.add(newStep);
	}

	public synchronized boolean deleteStep(double startPrice, double endPrice) {
		boolean stepExists = false;
		for(Step s: allPriceSteps) {
			if((s.getStartPrice() == startPrice) && (s.getEndPrice() == endPrice)) {
				stepExists = true;
				allPriceSteps.remove(s);
				break;
			}
		}
		return stepExists;
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
