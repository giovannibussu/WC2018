package worldcup.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Girone extends AbstractSubTorneo {

	public Girone(String name, int nTeams, int nTeamsPassaggioTurno) {
		super(name, nTeams, nTeamsPassaggioTurno,TYPE.GIRONE);
	}

	protected GironePerformance newIPerformance(Team team) {
		return new GironePerformance(this, team);
	}
	
	public int getPoints(Match match, Team team) {
		if(match.isPlayed()) {
			if(match.isDraw()) return 1;
			if(team.equals(match.getWinner())) {
				return 3;
			} else	return 0;
		}else return 0;
	}

	@Override
	public String toString() {
		List<GironePerformance> values = Arrays.asList(this.getTeamPerformances().values().toArray(new GironePerformance[]{}));
		Collections.sort(values);
		StringBuffer sb = new StringBuffer();
		sb.append("Name\tgp\tpts\tgd\tgf\tga\n");
		for(GironePerformance team: values) {
			sb.append(team.getTeam().getNome()+ "\t" + team.getPlayed()+ "\t" + team.getPoints()+ "\t" + team.getGoalsDifference()+ "\t" + team.getGoalsDone()+ "\t" + team.getGoalsTaken() + "\n");
		}
		return sb.toString();
	}
	@Override
	public boolean isDrawable() {
		return true;
	}

}
