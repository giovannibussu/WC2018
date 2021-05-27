package worldcup.core;

import java.util.List;
import java.util.Map;

import worldcup.core.AbstractSubTorneo.TYPE;
import worldcup.core.model.Match;
import worldcup.core.model.Team;

public class PositionableReport {

	private TYPE type;

	public List<Team> getPassaggioTurno() {
		return passaggioTurno;
	}
	public void setPassaggioTurno(List<Team> passaggioTurno) {
		if(this.passaggioTurno == null)
			this.passaggioTurno = passaggioTurno;
		else
			this.passaggioTurno.addAll(passaggioTurno);

	}
	public Map<String, Team> getPosizioni() {
		return posizioni;
	}
	public void setPosizioni(Map<String, Team> posizioni) {
		if (this.posizioni == null)
			this.posizioni = posizioni;
		else
			this.posizioni.putAll(posizioni);
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
		this.type =type;
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

	public int getPoints(PositionableReport original, boolean showPassaggiTurnoConf) {
		PositionableResult result = this.getResult(original, showPassaggiTurnoConf);
		
//		System.out.println("Risultati azzeccati: "+ result.getRisultato());
//		System.out.println("Risultati esatti azzeccati: "+ result.getRisultatoEsatto());
//		System.out.println("Passaggi turno azzeccati: "+ result.getPassaggioTurno());
//		System.out.println("Posizioni azzeccate: "+ result.getPosizioneTabellone());
		int points = 0;
		points+= result.getPassaggioTurno() * this.conf.getPassaggioTurno();
		points+= result.getPosizioneTabellone() * this.conf.getPosizioneTabellone();
		points+= result.getRisultato() * this.conf.getRisultato();
		points+= result.getRisultatoEsatto() * this.conf.getRisultatoEsatto();
		return points;
	}

	private PositionableResult getResult(PositionableReport original, boolean showPassaggiTurnoConf) {
		PositionableResult result = new PositionableResult();
//		System.out.println("--- Report "+this.type);
//		System.out.println("*** Calcolo Risultati ***");
		for(Match match: matches.keySet()) {
//			System.out.println(" Match : "+match);
			Match other = getEqui(original.getMatches(), match);
			if(other!=null) {
//				System.out.println(" Match : "+match);
				if(match.isPlayed() && other.isPlayed()) {
//					System.out.println("  Risultato  :"+(match.getResult().getRisultato()));
//					System.out.println("  Pronostico :"+(other.getResult().getRisultato()));
					if(match.isRisultato(other)) {
//						System.out.println("   Risultato Indovinato");
						result.addRisultato();
					} else {						
//						System.out.println("   Risultato NON indovinato");
					}
//					System.out.println("  Risultato esatto  :"+(match.getResult().getRisultatoEsatto()));
//					System.out.println("  Pronostico risutato esatto :"+(other.getResult().getRisultatoEsatto()));
					if(match.isRisultatoEsatto(other)) {
//						System.out.println("   Risultato Esatto Indovinato");
						result.addRisultatoEsatto();
					} else {
//						System.out.println("   Risultato Esatto NON Indovinato");
					}
				} else {
//					System.out.println("  Match non giocato o non pronosticao");
				}

			} else {
//				System.out.println("  Match non Equi");
			}
		}
//		System.out.println("************************");
//		System.out.println("");

//		if(showPassaggiTurnoConf && original.isPlayed()) {
		if(showPassaggiTurnoConf) {
//			System.out.println("*** Calcolo Passaggi ***");
			if(passaggioTurno!=null) {
//				System.out.print("  Risultato : ");
				if(original.getPassaggioTurno()!= null) {
					for (Team t : original.getPassaggioTurno()) {
//						System.out.print(t.getNome()+" ");
					}
//					System.out.println();
				}

				for(Team team: passaggioTurno) {

//					System.out.println("  Pronostico  : "+team.getNome());

					if(original.getPassaggioTurno()!= null && original.getPassaggioTurno().contains(team)) {
//						System.out.println("   Passaggio Indovinato");
						result.addPassaggioTurno();
					} else {
//						System.out.println("   Passaggio NON Indovinato");
					}
				}
			}
//			System.out.println("************************");
//			System.out.println("");
//			System.out.println("*** Calcolo Posizioni ***");
			if(posizioni!=null && !posizioni.isEmpty()) {
				for(String k: posizioni.keySet()) {
					if(original.getPosizioni() !=null )
					{
//						System.out.println("Posizione : "+k);
						Team t1 =original.getPosizioni().get(k);
//						System.out.println("  Risultato  :"+((t1 != null) ? t1.getNome() : "NULL" ));

						Team t2= this.getPosizioni().get(k);
//						System.out.println("  Pronostico :"+((t2 != null) ? t2.getNome() : "NULL" ));
						if (t1 != null && t2 != null && t1.equals(t2)) {
//							System.out.println("   Posizione Indovinata");
							result.addPosizioneTabellone();
						} else {
//							System.out.println("   Posizione NON indovinata");
						}
					}
				}
//			} else {
//				System.out.println("Posizioni nulle");
			}
//			System.out.println("*************************");
		}
//		System.out.println("");
		return result;
	}
	
	public boolean isPlayed() {
		for(Match p:this.matches.keySet()) {
			if(!p.isPlayed())
				return false;
		}
		return true;
	} 

	private Match getEqui(Map<Match, MatchResult> matches, Match orig) {
		for(Match match: matches.keySet()) {
			if(match.equi(orig))
				return match;
		}
		return null;
	}

}
