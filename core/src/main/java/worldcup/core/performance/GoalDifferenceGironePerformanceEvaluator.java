package worldcup.core.performance;

import worldcup.core.GironePerformance;

public class GoalDifferenceGironePerformanceEvaluator implements IPerformanceEvaluator<GironePerformance> {

	@Override
	public int evaluate(GironePerformance performance1, GironePerformance performance2) {
		return performance1.getGoalsDifference()-performance2.getGoalsDifference();
	}

}
