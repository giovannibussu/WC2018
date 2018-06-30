package worldcup.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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
		this.subTorneoLst = new TreeMap<>();

		for(Match match: map.values()) {
			this.subTorneoLst.put(match.getTorneo().getName(), match.getTorneo());
		}

	}

	public void play(String match, int home, int away) {
		this.matches.get(match).play(home, away);
	}

	public int getPoints(Torneo official) {
		refreshReports();

		PositionableReport thisReport = this.reports.get(TYPE.GIRONE);

		PositionableReport officialReport = official.getReports().get(TYPE.GIRONE);

		WorldCupProperties props = WorldCupProperties.getInstance();
		int pointsGironi = thisReport.getPoints(officialReport, props.isMostraPuntiPassaggioGironi());	
//		System.out.println("Punti gironi: " + pointsGironi);
		thisReport = this.reports.get(TYPE.OTTAVI);
		officialReport = official.getReports().get(TYPE.OTTAVI);
		int pointsOttavi = thisReport.getPoints(officialReport, props.isMostraPuntiPassaggioOttavi());	
//		System.out.println("Punti dopo gli ottavi: " + pointsOttavi);

		thisReport = this.reports.get(TYPE.QUARTI);
		officialReport = official.getReports().get(TYPE.QUARTI);
		int pointsQuarti = thisReport.getPoints(officialReport, props.isMostraPuntiPassaggioQuarti());	

		thisReport = this.reports.get(TYPE.SEMIFINALI);
		officialReport = official.getReports().get(TYPE.SEMIFINALI);
		int pointsSemifinale = thisReport.getPoints(officialReport, props.isMostraPuntiPassaggioSemifinali());	

		thisReport = this.reports.get(TYPE.FINALE);
		officialReport = official.getReports().get(TYPE.FINALE);
		int pointsFinale = thisReport.getPoints(officialReport, props.isMostraPuntiPassaggioFinale());	

		//		for(TYPE k: this.reports.keySet()) {
		//			PositionableReport thisReport = this.reports.get(k);
		//			PositionableReport officialReport = official.getReports().get(k);
		//			points += thisReport.getPoints(officialReport);
		//		}

		return pointsGironi + pointsOttavi + pointsQuarti + pointsSemifinale + pointsFinale;
	}

	private void refreshReports() {
		if(this.reports == null ) {
			Map<TYPE, List<AbstractSubTorneo>> positionables = this.subTorneoLst.values().stream().collect(Collectors.groupingBy(AbstractSubTorneo::getType));

			List<PositionableGroup> groups = new ArrayList<PositionableGroup>();
			//girone
			PositionableGroup girone = new PositionableGroup(TYPE.GIRONE, positionables.get(TYPE.GIRONE), positionables.get(TYPE.GIRONE));
			groups.add(girone);
			//ottavi
			PositionableGroup ottavi = new PositionableGroup(TYPE.OTTAVI, positionables.get(TYPE.OTTAVI), positionables.get(TYPE.QUARTI));
			groups.add(ottavi);
			//quarti
			PositionableGroup quarti = new PositionableGroup(TYPE.QUARTI, positionables.get(TYPE.QUARTI), positionables.get(TYPE.SEMIFINALI));
			groups.add(quarti);
			//semifinali
			PositionableGroup semifinali = new PositionableGroup(TYPE.SEMIFINALI, positionables.get(TYPE.SEMIFINALI), positionables.get(TYPE.FINALE));
			groups.add(semifinali);
			//finale
			PositionableGroup finale = new PositionableGroup(TYPE.FINALE, positionables.get(TYPE.FINALE), null);
			groups.add(finale);

			this.reports = new HashMap<>();

			Collections.sort(groups);
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
