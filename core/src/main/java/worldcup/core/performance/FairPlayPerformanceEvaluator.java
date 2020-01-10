package worldcup.core.performance;

import worldcup.core.GironePerformance;

public class FairPlayPerformanceEvaluator implements IPerformanceEvaluator<GironePerformance> {

	@Override
	public int evaluate(GironePerformance performance1, GironePerformance performance2) {
		return performance1.getTeam().getFairPlay()-performance2.getTeam().getFairPlay();
	}

}
