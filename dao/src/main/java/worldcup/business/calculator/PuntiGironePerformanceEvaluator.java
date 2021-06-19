package worldcup.business.calculator;

public class PuntiGironePerformanceEvaluator implements IPerformanceEvaluator<GironePerformance> {

	@Override
	public int evaluate(GironePerformance performance1, GironePerformance performance2) {
		return performance1.getPunti()-performance2.getPunti();
	}

}
