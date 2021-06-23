package worldcup.business.calculator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

		return merge(lstLst, lstPerformances);

	}

	private List<List<GironePerformance>> merge(List<List<GironePerformance>> lstLst,
			List<List<GironePerformance>> lstPerformances) {

		List<List<GironePerformance>> out = new ArrayList<>();
		for(List<GironePerformance> lst: lstLst) {
			List<GironePerformance> lst2 = new ArrayList<>();
			for(GironePerformance p: lst) {
				lst2.add(find(p.getSquadra().getNome(), lstPerformances));
			}
			out.add(lst2);
		}
		
		return out;
	}

	private GironePerformance find(String nome, List<List<GironePerformance>> lstLst) {
		for(List<GironePerformance> lst: lstLst) {
			for(GironePerformance p: lst) {
				if(p.getSquadra().getNome().equals(nome)) {
					return p;
				}
			}
		}
		
		throw new RuntimeException("Squadra ["+nome+"] non trovata");
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
			
			GironePerformance p2 =  p.getAvulsa(squadre);
			out.add(p2);
			
		}
		
		return out;
	}

}
