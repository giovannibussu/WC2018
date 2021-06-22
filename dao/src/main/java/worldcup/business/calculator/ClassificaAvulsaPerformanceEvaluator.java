package worldcup.business.calculator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

public class ClassificaAvulsaPerformanceEvaluator implements IPerformanceEvaluator<GironePerformance> {

	private Regole regole;

	public ClassificaAvulsaPerformanceEvaluator(Regole regoleClassificaAvulsa) {
		this.regole = regoleClassificaAvulsa;
	}

	@Override
	public List<List<GironePerformance>> reduce(List<List<GironePerformance>> lstPerformances) {
		
		List<List<GironePerformance>> lstLst = filter(lstPerformances);
		for(int i =0; i < this.regole.getRegole().size(); i++) {
			lstLst = this.regole.getRegole().get(i).reduce(lstLst);
		}

		
		return lstLst;

	}

	private List<List<GironePerformance>> filter(List<List<GironePerformance>> lstPerformances) {
		List<List<GironePerformance>> out = new ArrayList<>();
		
		for(List<GironePerformance> lst: lstPerformances) {
			out.add(redduce(lst));
		}
		
		return out;
	}

	private List<GironePerformance> redduce(List<GironePerformance> lst) {
		List<GironePerformance> out = new ArrayList<>();
		Set<String> squadre = new HashSet<String>();
		
		squadre.addAll(lst.stream().map(p -> p.getSquadra().getNome()).collect(Collectors.toList()));
		
		for(GironePerformance p: lst) {
			
			GironePerformance p2 = new GironePerformance();
			p2.setGirone(p.getGirone());
			p2.setSquadra(p.getSquadra());
			
			for(Entry<String, GironeSingleMatchPerformance> e: p.getPerformances().entrySet()) {
				if(squadre.contains(e.getKey())) {
					p2.getPerformances().put(e.getKey(), e.getValue());
				}
			}
			out.add(p2);
			
		}
		
		return out;
	}

}
