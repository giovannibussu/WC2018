package worldcup.business.calculator;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GironePerformanceComparator {//implements Comparator<GironePerformance> {

	private Regole regole;
	private Regole regoleClassificaAvulsa;

	public Regole getRegole() {
		return regole;
	}
	public void setRegole(Regole regole) {
		this.regole = regole;
	}

//	@Override
//	public int compare(GironePerformance perf1, GironePerformance perf2) {
//		return -1 * _compare(perf1, perf2);
//	}
//	
//	public int _compare(GironePerformance perf1, GironePerformance perf2) {
//		for(int i =0; i < this.regole.getRegole().size(); i++) {
//			int iEvaluate = this.regole.getRegole().get(i).evaluate(perf1, perf2);
//			if(iEvaluate != 0) {
//				return iEvaluate;
//			}
//		}
//		
//		return 0;
//	}
	
	public Map<Integer, GironePerformance> sort(Collection<GironePerformance> performances) {
		
		List<List<GironePerformance>> lstLst = Arrays.asList(performances.stream().collect(Collectors.toList()));

		for(int i =0; i < this.regole.getRegole().size(); i++) {
			lstLst = this.regole.getRegole().get(i).reduce(lstLst);
		}

		
		Map<Integer, GironePerformance> squadre = new HashMap<Integer, GironePerformance>();
		for(int i = 0; i < lstLst.size(); i++) {
			
			if(lstLst.get(i).size() > 1) {
				throw new RuntimeException("Ambiguita nel girone ["+lstLst.get(i).get(0).getGirone()+"]: "+lstLst.get(i).get(0).getSquadra().getNome());
			}
			squadre.put(i+1, lstLst.get(i).get(0));
		}
		
		return squadre;
	}
	public Regole getRegoleClassificaAvulsa() {
		return regoleClassificaAvulsa;
	}
	public void setRegoleClassificaAvulsa(Regole regoleClassificaAvulsa) {
		this.regoleClassificaAvulsa = regoleClassificaAvulsa;
	}

}
