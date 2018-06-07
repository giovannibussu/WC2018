package worldcup.core;

import java.util.List;
import java.util.Map;

import worldcup.core.AbstractSubTorneo.TYPE;

public class PositionableReport {

	public List<Team> getPassaggioTurno() {
		return passaggioTurno;
	}
	public void setPassaggioTurno(List<Team> passaggioTurno) {
		this.passaggioTurno = passaggioTurno;
	}
	public Map<String, Team> getPosizioni() {
		return posizioni;
	}
	public void setPosizioni(Map<String, Team> posizioni) {
		this.posizioni = posizioni;
	}
	public Map<Match, MatchResult> getMatches() {
		return matches;
	}
	public void setMatches(Map<Match, MatchResult> matches) {
		this.matches = matches;
	}
	private List<Team> passaggioTurno;
	private Map<String, Team> posizioni;
	private Map<Match, MatchResult> matches;
	private PositionableConfiguration conf;

	public PositionableReport(TYPE type) {
		switch(type) {
		case FINALE: conf = PositionableConfigurationFactory.getFinaleConfiguration();
			break;
		case GIRONE: conf = PositionableConfigurationFactory.getGironeConfiguration();
			break;
		case OTTAVI: conf = PositionableConfigurationFactory.getOttaviConfiguration();
			break;
		case QUARTI: conf = PositionableConfigurationFactory.getQuartiConfiguration();
			break;
		case SEMIFINALI: conf = PositionableConfigurationFactory.getSemifinaleConfiguration();
			break;
		}
		
	}
	
	public int getPoints(PositionableReport original) {
		PositionableResult result = this.getResult(original);
		int points = 0;
		points+= result.getPassaggioTurno() * conf.getPassaggioTurno();
		points+= result.getPosizioneTabellone() * conf.getPosizioneTabellone();
		points+= result.getRisultato() * conf.getRisultato();
		points+= result.getRisultatoEsatto() * conf.getRisultatoEsatto();
		return points;
	}
	
	private PositionableResult getResult(PositionableReport original) {
		PositionableResult result = new PositionableResult();
		
		for(Match match: matches.keySet()) {
			Match other = getEqui(original.getMatches(), match);
			
			if(other!=null) {
				if(match.isPlayed() && other.isPlayed()) {
					if(match.getResult().getRisultato().equals(other.getResult().getRisultato()))
						result.addRisultato();
					if(match.getResult().getRisultatoEsatto().equals(other.getResult().getRisultatoEsatto()))
						result.addRisultatoEsatto();
				}
			}
		}
		
		if(passaggioTurno!=null)
			for(Team team: passaggioTurno) {
				if(original.getPassaggioTurno()!= null && original.getPassaggioTurno().contains(team))
					result.addPassaggioTurno();
			}
		
		if(posizioni!=null)
			for(String k: posizioni.keySet()) {
				if(original.getPosizioni() !=null )
				{
					
					Team t1 =original.getPosizioni().get(k);
					Team t2= this.getPosizioni().get(k);
					if (t1 != null && t2 != null && t1.equals(t2))
					  result.addPosizioneTabellone();
				}
			}
		
		return result;
	}
	private Match getEqui(Map<Match, MatchResult> matches, Match orig) {
		for(Match match: matches.keySet()) {
			if(match.equi(orig))
				return match;
		}
		return null;
	}
	
}
