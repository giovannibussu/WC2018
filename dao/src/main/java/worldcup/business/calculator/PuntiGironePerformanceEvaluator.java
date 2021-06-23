package worldcup.business.calculator;

public class PuntiGironePerformanceEvaluator extends AbstractPerformanceEvaluator {

	protected Integer calculatePerformance(GironePerformance performance) {
		return performance.getPunti();
	}

}
