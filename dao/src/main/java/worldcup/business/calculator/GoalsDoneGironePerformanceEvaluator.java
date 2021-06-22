package worldcup.business.calculator;

public class GoalsDoneGironePerformanceEvaluator extends AbstractPerformanceEvaluator {

	protected Integer calculatePerformance(GironePerformance performance) {
		return performance.getGoalFatti();
	}

}
