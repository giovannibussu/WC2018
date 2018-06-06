package worldcup.core;

public class PartitaGironePerformance implements Comparable<PartitaGironePerformance>{

	private int points;
	private int goalsDone;
	private int goalsTaken;
	public PartitaGironePerformance(int points, int goalsDone, int goalsTaken) {
		this.points = points;
		this.goalsDone =goalsDone;
		this.goalsTaken = goalsTaken;
	}

	public int compareTo(PartitaGironePerformance against) {
		return -1* _compareTo(against); // ordine descrescente invece che crescente
	}

	public int _compareTo(PartitaGironePerformance against) {
		if(this.points!=against.getPoints()) { // 1.punti fatti
			return this.points-against.getPoints();
		} else {
			if(this.getGoalsDifference()!=against.getGoalsDifference()) {  // 2. differenza reti totale
				return this.getGoalsDifference() - against.getGoalsDifference();
			} else {
				if(this.goalsDone != against.getGoalsDone()) { // 3. goal fatti totali
					return this.goalsDone - against.getGoalsDone();
				} else {
					return 0;
				}
			}
		}
	}

	public int getPoints() {
		return points;
	}

	public int getGoalsDone() {
		return goalsDone;
	}

	public int getGoalsTaken() {
		return goalsTaken;
	}

	public int getGoalsDifference() {
		return goalsDone - goalsTaken;
	}

}
