package worldcup.core.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import worldcup.core.AbstractSubTorneo;
import worldcup.core.GironePerformance;

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
		sb.append("Girone " + this.getName()).append("\n");
		sb.append("Classifica:\n");
		sb.append("Name\tgp\tpts\tgd\tgf\tga\n");
		for(GironePerformance team: values) {
			sb.append(team.getTeam().getId()+ "\t" + team.getPlayed()+ "\t" + team.getPoints()+ "\t" + team.getGoalsDifference()+ "\t" + team.getGoalsDone()+ "\t" + team.getGoalsTaken() + "\n");
		}

		List<Match> daGiocare = this.getMatches().stream().filter(a -> !a.isPlayed()).collect(Collectors.toList());
		List<Match> giocate = this.getMatches().stream().filter(a -> a.isPlayed()).collect(Collectors.toList());
		if(daGiocare.size() > 0) {
			sb.append("Partite da giocare:\n");
			
		Collections.sort(daGiocare);
		for(Match match: daGiocare) {
				sb.append(match).append("\n");
			}
		}
		
		if(giocate.size()>0) {
			sb.append("Partite giocate:\n");
			Collections.sort(giocate);
			for(Match match: giocate) {
				sb.append(match).append("\n");
			}
		}
		return sb.toString();
	}

	public Map<String, Team> getPosizioni() {
		Map<String, Team> posizioni = new HashMap<String, Team>();
		for(int i = 0; i < this.getnTeams(); i++) {
			posizioni.put(this.getName() + "-"+i, this.getAtPosition(i));
		}
		return posizioni;
	}

	@Override
	public boolean isDrawable() {
		return true;
	}

	
	 

}
