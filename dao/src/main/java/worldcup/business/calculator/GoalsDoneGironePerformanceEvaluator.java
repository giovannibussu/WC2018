package worldcup.business.calculator;

public class GoalsDoneGironePerformanceEvaluator implements IPerformanceEvaluator<GironePerformance> {

	@Override
	public int evaluate(GironePerformance performance1, GironePerformance performance2) {
		return performance1.getGoalFatti()-performance2.getGoalFatti();
	}

}
