package worldcup.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import worldcup.core.AbstractSubTorneo.TYPE;

public class Torneo extends JsonSerializable {

	private Map<String, Match> map;
	private List<AbstractSubTorneo> subTorneoLst;
	private Map<TYPE, PositionableReport> reports;
	public Torneo(Map<String, Match> map) {
		
		this.map=map;
		this.subTorneoLst=map.values().stream().map(a-> a.getTorneo()).collect(Collectors.toList());
	}
	
	public void play(String match, int home, int away) {
		this.map.get(match).play(home, away);
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
			Map<TYPE, List<AbstractSubTorneo>> positionables = this.subTorneoLst.stream().collect(Collectors.groupingBy(AbstractSubTorneo::getType));
	
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
	public List<AbstractSubTorneo> getSubTorneoLst() {
		return this.subTorneoLst;
	}

	public Map<TYPE, PositionableReport> getReports() {
		this.refreshReports();
		return this.reports;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(AbstractSubTorneo p: this.subTorneoLst) {
			sb.append(p).append("\n");
		}
		return sb.toString();
	}
}
