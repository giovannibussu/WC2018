package worldcup.business.calculator;

import java.util.List;

public interface IPerformanceEvaluator<T> {

//	public int evaluate(T performance1, T performance2);
	public List<List<T>> reduce(List<List<T>> performanceList);
}
