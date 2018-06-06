package worldcup.core;

public class KnockoutPerformance implements IPerformance{

	private Team team;
	private int goalsDone;
	
	public KnockoutPerformance(Team team) {
		this.team = team;
		this.goalsDone = 0;
	}
	
	public int compareTo(IPerformance o) {
		return -1* this._compareTo((KnockoutPerformance)o);
	}
	
	public int _compareTo(KnockoutPerformance o) {
		if(this.goalsDone == o.getGoalsDone())
			throw new RuntimeException("Il pareggio non e' ammesso");
		return this.goalsDone - o.getGoalsDone();
	}
	
	

	public void update(Match match, IPerformance performance) {
		goalsDone+= match.getGoalsDone(team);
	}

	public Team getTeam() {
		return this.team;
	}

	public int getGoalsDone() {
		return goalsDone;
	}

}
