package worldcup.business.calculator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractPerformanceEvaluator implements IPerformanceEvaluator<GironePerformance> {

	@Override
	public List<List<GironePerformance>> reduce(List<List<GironePerformance>> performanceListList) {
		
//		System.out.println("Evaluator " + this.getClass().getName());
//		System.out.println("input " + performanceListList.size());
//		performanceListList.stream().forEach(p -> {
//			System.out.println("----------------------------");
//			p.stream().forEach(p2 -> {
//				System.out.println("Performances: " + p2.getPerformances().size());
//				System.out.println("Squadra: " + p2.getSquadra().getNome() + " performance: " + calculatePerformance(p2));
//			});
//			System.out.println("----------------------------");
//		});
		
		
		
		
		
		
		
		
		List<List<GironePerformance>> out = new ArrayList<List<GironePerformance>>();
		for(List<GironePerformance> performanceList: performanceListList) {
			if(performanceList.size() == 1) {
				out.add(performanceList);
			} else {
				out.addAll(_reduce(performanceList));
			}
		}
		
		
		
		
		
		
//		
//		System.out.println("output " + out.size());
//		out.stream().forEach(p -> {
//			System.out.println("----------------------------");
//			p.stream().forEach(p2 -> {
//				System.out.println("Squadra: " + p2.getSquadra().getNome());
//			});
//			System.out.println("----------------------------");
//		});
		
		return out;
	}

	private List<List<GironePerformance>> _reduce(List<GironePerformance> performanceList) {
		
		Map<Integer, List<GironePerformance>> performancesMap = new HashMap<>();
		
		for(GironePerformance performance: performanceList) {
			Integer performanceScore = calculatePerformance(performance);
			
			if(performancesMap.containsKey(performanceScore)) {
				performancesMap.get(performanceScore).add(performance);
			} else {
				List<GironePerformance> performanceLst = new ArrayList<GironePerformance>();
				performanceLst.add(performance);
				performancesMap.put(performanceScore,performanceLst);
			}
		}
		
		return performancesMap.entrySet().stream().sorted(Map.Entry.comparingByKey(Comparator.reverseOrder())).map(e -> e.getValue()).collect(Collectors.toList());
	}
	
	protected abstract Integer calculatePerformance(GironePerformance performance);

}
