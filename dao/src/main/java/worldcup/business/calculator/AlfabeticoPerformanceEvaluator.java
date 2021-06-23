package worldcup.business.calculator;

public class AlfabeticoPerformanceEvaluator extends AbstractPerformanceEvaluator {

	protected Integer calculatePerformance(GironePerformance performance) {
		return performance.getSquadra().getId().intValue();
	}

}
