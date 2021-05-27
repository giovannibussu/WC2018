package worldcup.core.performance;

import java.util.ArrayList;
import java.util.List;

import worldcup.core.GironePerformance;

public class PerformanceEvaluatorUtils {

	public static List<IPerformanceEvaluator<GironePerformance>> getRegoleGirone() {
		List<IPerformanceEvaluator<GironePerformance>> regole = new ArrayList<IPerformanceEvaluator<GironePerformance>>();
		
		regole.add(new PuntiGironePerformanceEvaluator());
		regole.add(new GoalDifferenceGironePerformanceEvaluator());
		regole.add(new GoalsDoneGironePerformanceEvaluator());
		regole.add(new ScontroDirettoPerformanceEvaluator());
		regole.add(new FairPlayPerformanceEvaluator());
		
		return regole;
	}
	
	public static int compareTo(GironePerformance p1, GironePerformance p2, List<IPerformanceEvaluator<GironePerformance>> lst) {

		for(int i =0; i < lst.size(); i++) {
			int iEvaluate = lst.get(i).evaluate(p1, p2);
			if(iEvaluate != 0) {
				return iEvaluate;
			}
		}
		
		return 0;

	}	
}
