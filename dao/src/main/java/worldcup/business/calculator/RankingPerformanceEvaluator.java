package worldcup.business.calculator;

public class RankingPerformanceEvaluator extends AbstractPerformanceEvaluator {

	protected Integer calculatePerformance(GironePerformance performance) {
		return performance.getSquadra().getRankingFifa();
	}

}
