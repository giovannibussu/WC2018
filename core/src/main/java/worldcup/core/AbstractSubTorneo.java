package worldcup.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractSubTorneo {

	public enum TYPE {GIRONE, OTTAVI, QUARTI, SEMIFINALI, FINALE}
	private Map<Team, IPerformance> teamPerformances;
	private List<MatchDependent> listMatchDependent;
	private Set<Match> matches;
	private String name;
	private int nTeams;
	private int nTeamsPassaggioTurno;
	private TYPE type;
	public Map<Team, IPerformance> getTeamPerformances() {
		return teamPerformances;
	}

	public void setTeamPerformances(Map<Team, IPerformance> teamPerformances) {
		this.teamPerformances = teamPerformances;
	}

	public AbstractSubTorneo(String name, int nTeams, int nTeamsPassaggioTurno, TYPE type) {
		this.type = type;
		this.name = name;
		this.teamPerformances = new HashMap<Team, IPerformance>();
		this.nTeams = nTeams;
		this.nTeamsPassaggioTurno = nTeamsPassaggioTurno;
		this.matches = new HashSet<Match>();
		this.listMatchDependent = new ArrayList<AbstractSubTorneo.MatchDependent>();
	}
	
	private void registerTeam(Team team) {
		this.teamPerformances.put(team, newIPerformance(team));
	}
	
	public void addMatch(Match match) {
		this.matches.add(match);
	}
	
	public void play(Match match, int homeGoals, int awayGoals) {
		if(match.isPlayable()) {
			
			if(!this.isDrawable() && homeGoals == awayGoals) {
				throw new RuntimeException("Pareggio non ammesso per la gara ["+this.getName()+"]");
			}
			this.matches.add(match);
			Team home = match.getHome();
			Team away = match.getAway();
			if(!this.teamPerformances.containsKey(home)) {
				this.registerTeam(home);
			} 
			IPerformance homeP = this.teamPerformances.get(home);
			
			if(!this.teamPerformances.containsKey(away)) {
				this.registerTeam(away);
			} 
			IPerformance awayP = this.teamPerformances.get(away);
	
			homeP.update(match, awayP);
			awayP.update(match, homeP);
			initMatches();
		}
	}

	protected abstract IPerformance newIPerformance(Team home);
	protected abstract boolean isDrawable();
	
	public Team getAtPosition(int index) {
		List<IPerformance> values = Arrays.asList(this.teamPerformances.values().toArray(new IPerformance[]{}));
		Collections.sort(values);
		return values.get(index).getTeam();
	}

	public boolean isPlayed() {
		return this.matches.stream()
				.filter(match -> match.isPlayed())
				.collect(Collectors.toList()).size() >= ((nTeams -1) * (nTeams /2));
	}
	
	public int getNTeamPassaggioTurno() {
		return nTeamsPassaggioTurno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Match> getMatches() {
		return matches;
	}

	public void setMatches(Set<Match> matches) {
		this.matches = matches;
	}
	
	public void registerMatch(Match match, boolean home, int position) {
		MatchDependent matchbypos = new MatchDependent();
		matchbypos.match = match;
		matchbypos.home = home;
		matchbypos.position = position;
		listMatchDependent.add(matchbypos);
	}
	
	public void initMatches() {
		if(this.isPlayed()) {
			for(MatchDependent mbp: listMatchDependent) {
				Team team = this.getAtPosition(mbp.position);
				if(mbp.home) {
					mbp.match.setHome(team);
				} else {
					mbp.match.setAway(team);
				}
			}
		}
	}

	public int getnTeams() {
		return nTeams;
	}

	class MatchDependent {
		Match match;
		boolean home;
		int position;
	}


	public Map<String, Team> getPosizioni() {
		Map<String, Team> posizioni = new HashMap<String, Team>();
		for(int i = 0; i < this.getnTeams(); i++) {
			posizioni.put(this.getName() + "-"+i, this.getAtPosition(i));
		}
		return posizioni;
	}

	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}

}
