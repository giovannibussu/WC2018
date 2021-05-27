package worldcup.core.performance;

public interface IPerformanceEvaluator<T> {

	public int evaluate(T performance1, T performance2);
}
