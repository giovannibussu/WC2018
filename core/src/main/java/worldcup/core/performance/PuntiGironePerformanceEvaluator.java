package worldcup.core.performance;

import worldcup.core.GironePerformance;

public class PuntiGironePerformanceEvaluator implements IPerformanceEvaluator<GironePerformance> {

	@Override
	public int evaluate(GironePerformance performance1, GironePerformance performance2) {
		return performance1.getPoints()-performance2.getPoints();
	}

}
