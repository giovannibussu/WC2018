package worldcup.core;

import java.util.HashMap;
import java.util.Map;

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
		if(this.getPoints()!=against.getPoints()) { // 1.punti fatti
			return this.getPoints()-against.getPoints();
		} else {
			if(this.getGoalsDifference()!=against.getGoalsDifference()) {  // 2. differenza reti totale
				return this.getGoalsDifference() - against.getGoalsDifference();
			} else {
				if(this.getGoalsDone() != against.getGoalsDone()) { // 3. goal fatti totali
					return this.getGoalsDone() - against.getGoalsDone();
				} else {
					if(this.singleMatch.containsKey(against.getTeam())) {
						int compareSingleMatch = this.singleMatch.get(against.getTeam()).compareTo(against.getSingleMatch().get(team));
						if(compareSingleMatch!=0) { // 4. scontro diretto
							return compareSingleMatch;
						} else {
							if(this.getTeam().getFairPlay() != against.getTeam().getFairPlay()) { // 5. fair play (per semplificare messo in configurazione delle squadre, comprende eventuale sorteggio)
								return this.getTeam().getFairPlay() - against.getTeam().getFairPlay();
							} else {
								return 0;
							}
						}
					} else {
						if(this.getTeam().getFairPlay() != against.getTeam().getFairPlay()) { // 5. fair play (per semplificare messo in configurazione delle squadre, comprende eventuale sorteggio)
							return this.getTeam().getFairPlay() - against.getTeam().getFairPlay();
						} else {
							return 0;
						}
					}
				}
			}
		}
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
