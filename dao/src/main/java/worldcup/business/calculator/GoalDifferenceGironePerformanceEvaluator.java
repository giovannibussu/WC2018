package worldcup.business.calculator;

public class GoalDifferenceGironePerformanceEvaluator implements IPerformanceEvaluator<GironePerformance> {

	@Override
	public int evaluate(GironePerformance performance1, GironePerformance performance2) {
		return performance1.getDifferenzaReti()-performance2.getDifferenzaReti();
	}

}
