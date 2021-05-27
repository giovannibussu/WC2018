package worldcup.core;

import java.util.HashMap;
import java.util.Map;

import worldcup.core.model.Girone;
import worldcup.core.model.Match;
import worldcup.core.model.Team;
import worldcup.core.performance.PerformanceEvaluatorUtils;

public class GironePerformance implements IPerformance {

	private Girone girone;
	private Team team;
	private Map<Team, PartitaGironePerformance> singleMatch;
	public GironePerformance(Girone girone, Team team) {
		this.team = team;
		this.singleMatch = new HashMap<Team, PartitaGironePerformance>();
		this.girone = girone;
	}

	public void update(Match match, IPerformance perf) {
		GironePerformance against = (GironePerformance) perf;
		int points = girone.getPoints(match, team);
		int goalsDone = match.getGoalsDone(team);
		int goalsTaken = match.getGoalsTaken(team);

		this.singleMatch.put(against.getTeam(), new PartitaGironePerformance(points, goalsDone, goalsTaken));
	}

	public int compareTo(IPerformance against) {
		return -1* _compareTo((GironePerformance)against); // ordine descrescente invece che crescente
	}

	public int _compareTo(GironePerformance against) {
		
		return PerformanceEvaluatorUtils.compareTo(this, against, PerformanceEvaluatorUtils.getRegoleGirone());
		
	}

	public Team getTeam() {
		return team;
	}

	public int getPoints() {
		return this.singleMatch.values().stream().mapToInt(a-> a.getPoints()).sum();
	}

	public int getGoalsDone() {
		return this.singleMatch.values().stream().mapToInt(a-> a.getGoalsDone()).sum();
	}

	public int getGoalsTaken() {
		return this.singleMatch.values().stream().mapToInt(a-> a.getGoalsTaken()).sum();
	}

	public int getGoalsDifference() {
		return this.singleMatch.values().stream().mapToInt(a-> a.getGoalsDifference()).sum();
	}

	public int getPlayed() {
		return this.singleMatch.size();
	}

	public Map<Team, PartitaGironePerformance> getSingleMatch() {
		return this.singleMatch;
	}

}
