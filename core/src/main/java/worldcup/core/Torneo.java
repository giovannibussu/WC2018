package worldcup.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import worldcup.core.AbstractSubTorneo.TYPE;

public class Torneo extends JsonSerializable {

	private Map<String, Match> matches;
	private Map<String, AbstractSubTorneo> subTorneoLst;
	private Map<TYPE, PositionableReport> reports;
	private String idFinale;
	public Torneo(Map<String, Match> map, String idFinale) {
		
		this.idFinale=idFinale;
		this.matches=map;
		this.subTorneoLst = new HashMap<>();

		for(Match match: map.values()) {
			this.subTorneoLst.put(match.getTorneo().getName(), match.getTorneo());
		}
		
	}
	
	public void play(String match, int home, int away) {
		this.matches.get(match).play(home, away);
	}

	public int getPoints(Torneo official) {
		refreshReports();
		
		int points = 0;
		for(TYPE k: this.reports.keySet()) {
			PositionableReport thisReport = this.reports.get(k);
			PositionableReport officialReport = official.getReports().get(k);
			points += thisReport.getPoints(officialReport);
		}
		
		return points;
	}

	private void refreshReports() {
		if(this.reports == null ) {
			Map<TYPE, List<AbstractSubTorneo>> positionables = this.subTorneoLst.values().stream().collect(Collectors.groupingBy(AbstractSubTorneo::getType));
	
			List<PositionableGroup> groups = new ArrayList<PositionableGroup>();
			
			for(TYPE t: positionables.keySet()) {
				PositionableGroup pos = new PositionableGroup(t, positionables.get(t));
				groups.add(pos);
			}
	
			this.reports = new HashMap<>();
			
			for(PositionableGroup group: groups) {
				this.reports.put(group.getType(), group.getReport());
			}
		}
		
	}

	public Team getWinner() {
		Match finale = this.matches.get(this.idFinale);
		if(finale.isPlayed())
			return finale.getWinner();
		else return null;
	}

	public Match getMatch(String name) {
		return this.matches.get(name);
	}

	public AbstractSubTorneo getAbstractSubTorneo(String name) {
		return this.subTorneoLst.get(name);
	}
	public Collection<AbstractSubTorneo> getSubTorneoLst() {
		return this.subTorneoLst.values();
	}

	public Map<TYPE, PositionableReport> getReports() {
		this.refreshReports();
		return this.reports;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(AbstractSubTorneo p: this.subTorneoLst.values()) {
			sb.append(p).append("\n");
		}
		return sb.toString();
	}

	public Map<String, Match> getMatches() {
		return matches;
	}
}
