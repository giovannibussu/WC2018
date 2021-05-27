package worldcup.core.performance;

import worldcup.core.GironePerformance;

public class ScontroDirettoPerformanceEvaluator implements IPerformanceEvaluator<GironePerformance> {

	@Override
	public int evaluate(GironePerformance performance1, GironePerformance performance2) {
		if(performance1.getSingleMatch().containsKey(performance2.getTeam())) {
			performance1.getSingleMatch().get(performance2.getTeam())._compareTo(performance2.getSingleMatch().get(performance1.getTeam()));
		}
		
		return 0;
	}

}
