package worldcup.business.calculator;

import java.util.Comparator;

public class GironePerformanceComparator implements Comparator<GironePerformance> {

	private Regole regole;

	public Regole getRegole() {
		return regole;
	}
	public void setRegole(Regole regole) {
		this.regole = regole;
	}

	@Override
	public int compare(GironePerformance perf1, GironePerformance perf2) {
		return -1 * _compare(perf1, perf2);
	}
	
	public int _compare(GironePerformance perf1, GironePerformance perf2) {
		for(int i =0; i < this.regole.getRegole().size(); i++) {
			int iEvaluate = this.regole.getRegole().get(i).evaluate(perf1, perf2);
			if(iEvaluate != 0) {
				return iEvaluate;
			}
		}
		
		return 0;
	}

}
