package worldcup.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import worldcup.core.AbstractSubTorneo.TYPE;

public class PositionableGroup {

	private List<AbstractSubTorneo> lstPos;
	private TYPE type;
	public PositionableGroup(TYPE type, List<AbstractSubTorneo> lstPos) {
		this.type = type;
		this.lstPos = lstPos;
	}
	
	public PositionableReport getReport() {
		PositionableReport positionableReport = new PositionableReport(this.type);
		Map<Match, MatchResult> matches = new HashMap<Match, MatchResult>();
		for(AbstractSubTorneo p: lstPos) {
			for(Match match: p.getMatches()) {
				matches.put(match, match.getResult());
			}
			if(p.isPlayed()) {
				List<Team> passaggioTurno = new ArrayList<Team>();
				for(int i =0; i < p.getNTeamPassaggioTurno();i++) {
					passaggioTurno.add(p.getAtPosition(i));
				}
				positionableReport.setPassaggioTurno(passaggioTurno);
				positionableReport.setPosizioni(p.getPosizioni());				
			}
		}
		
		positionableReport.setMatches(matches);
		return positionableReport;
	}

	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}
}
