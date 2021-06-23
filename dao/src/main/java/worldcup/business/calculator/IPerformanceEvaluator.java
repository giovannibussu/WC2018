package worldcup.business.calculator;

import java.util.List;

public interface IPerformanceEvaluator<T> {

	public List<List<T>> reduce(List<List<T>> performanceList);
}
