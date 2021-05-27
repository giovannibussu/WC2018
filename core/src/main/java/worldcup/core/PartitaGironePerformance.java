package worldcup.core;

import java.util.ArrayList;
import java.util.List;

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
		
		List<Integer> lstCriteri = new ArrayList<>();
		
		lstCriteri.add(this.points-against.getPoints()); // 1.punti fatti
		lstCriteri.add(this.getGoalsDifference() - against.getGoalsDifference()); // 2. differenza reti totale
		lstCriteri.add(this.goalsDone - against.getGoalsDone()); // 3. goal fatti totali
		
		for(int i =0; i < lstCriteri.size(); i++) {
			if(lstCriteri.get(i) != 0) {
				return lstCriteri.get(i);
			}
		}
		
		return 0;
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
