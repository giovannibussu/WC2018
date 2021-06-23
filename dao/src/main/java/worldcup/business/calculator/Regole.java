package worldcup.business.calculator;

import java.util.List;

public class Regole {
	private List<IPerformanceEvaluator<GironePerformance>> regole;

	public List<IPerformanceEvaluator<GironePerformance>> getRegole() {
		return regole;
	}

	public void setRegole(List<IPerformanceEvaluator<GironePerformance>> regole) {
		this.regole = regole;
	}
}
