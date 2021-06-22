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
		List<List<GironePerformance>> out = new ArrayList<List<GironePerformance>>();
		for(List<GironePerformance> performanceList: performanceListList) {
			if(performanceList.size() == 1) {
				out.add(performanceList);
			} else {
				out.addAll(_reduce(performanceList));
			}
		}
		
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
