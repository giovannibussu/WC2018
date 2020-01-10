package worldcup.core.performance;

import worldcup.core.GironePerformance;

public class GoalsDoneGironePerformanceEvaluator implements IPerformanceEvaluator<GironePerformance> {

	@Override
	public int evaluate(GironePerformance performance1, GironePerformance performance2) {
		return performance1.getGoalsDone()-performance2.getGoalsDone();
	}

}
