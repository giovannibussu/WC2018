package worldcup.business.calculator;

public class GoalDifferenceGironePerformanceEvaluator extends AbstractPerformanceEvaluator {

	protected Integer calculatePerformance(GironePerformance performance) {
		return performance.getDifferenzaReti();
	}

}
