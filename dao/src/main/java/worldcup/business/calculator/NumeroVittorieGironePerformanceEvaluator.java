package worldcup.business.calculator;

public class NumeroVittorieGironePerformanceEvaluator extends AbstractPerformanceEvaluator {

	protected Integer calculatePerformance(GironePerformance performance) {
		return performance.getVittorie();
	}

}
