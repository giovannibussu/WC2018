package worldcup.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import worldcup.core.AbstractSubTorneo.TYPE;
import worldcup.core.model.Match;
import worldcup.core.model.Team;

public class PositionableGroup implements Comparable<PositionableGroup> {

	private List<AbstractSubTorneo> lstPos;
	private List<AbstractSubTorneo> lstPosNext;
	private TYPE type;
	public PositionableGroup(TYPE type, List<AbstractSubTorneo> lstPos, List<AbstractSubTorneo> lstPosNext) {
		this.type = type;
		this.lstPos = lstPos;
		this.lstPosNext = lstPosNext;
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
			}
		}

		if(this.lstPosNext!=null) {
			for(AbstractSubTorneo p: lstPosNext) {
				if((type.equals(TYPE.GIRONE) && p.isPlayed()) || (!type.equals(TYPE.GIRONE) && p.isPlayable())) {
					positionableReport.setPosizioni(p.getPosizioni());				
				}
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
	
	
	public int compareTo(PositionableGroup o) {
		if(o.getType() == getType())
			return 0;
		
		if ( getType() == AbstractSubTorneo.TYPE.OTTAVI) {
			if ( o.getType() == AbstractSubTorneo.TYPE.QUARTI || o.getType() == AbstractSubTorneo.TYPE.SEMIFINALI || o.getType() == AbstractSubTorneo.TYPE.FINALE) {
				return -1;
			}

		}
		else if ( getType() == AbstractSubTorneo.TYPE.QUARTI) {
			if (  o.getType() == AbstractSubTorneo.TYPE.SEMIFINALI || o.getType() == AbstractSubTorneo.TYPE.FINALE) {
				return -1;
			}

		}
		else if ( getType() == AbstractSubTorneo.TYPE.SEMIFINALI) {
			if ( o.getType() == AbstractSubTorneo.TYPE.FINALE) {
				return -1;
			}

		} 
		
		return 1;
	}
}
