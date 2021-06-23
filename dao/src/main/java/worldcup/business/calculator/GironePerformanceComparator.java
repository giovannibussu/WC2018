package worldcup.business.calculator;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GironePerformanceComparator {

	private Regole regole;
//	private boolean revert;

//	public GironePerformanceComparator(boolean revert) {
//		this.revert = revert;
//	}
	public Regole getRegole() {
		return regole;
	}
	public void setRegole(Regole regole) {
		this.regole = regole;
	}

	public Map<Integer, GironePerformance> sort(Collection<GironePerformance> performances) {
		
		List<List<GironePerformance>> lstLst = Arrays.asList(performances.stream().collect(Collectors.toList()));

		for(int i =0; i < this.regole.getRegole().size(); i++) {
			lstLst = this.regole.getRegole().get(i).reduce(lstLst);
		}

		
		Map<Integer, GironePerformance> squadre = new HashMap<Integer, GironePerformance>();
		
//		if(revert) {
//			for(int i = 0; i < lstLst.size(); i++) {
//				
//				if(lstLst.get(i).size() > 1) {
//					throw new RuntimeException("Ambiguita nel girone ["+lstLst.get(i).get(0).getGirone()+"]: "+lstLst.get(i).get(0).getSquadra().getNome());
//				}
//				squadre.put(7 - (i+1), lstLst.get(i).get(0));
//			}
//		} else {
			for(int i = 0; i < lstLst.size(); i++) {
				
				if(lstLst.get(i).size() > 1) {
					throw new RuntimeException("Ambiguita nel girone ["+lstLst.get(i).get(0).getGirone()+"]: "+lstLst.get(i).get(0).getSquadra().getNome());
				}
				squadre.put(i+1, lstLst.get(i).get(0));
			}
//		}
		
		return squadre;
	}

}
