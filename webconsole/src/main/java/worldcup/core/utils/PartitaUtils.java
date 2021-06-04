package worldcup.core.utils;

import worldcup.clients.model.Partita;
import worldcup.clients.model.PronosticoRisultato;
import worldcup.clients.model.Risultato;

public class PartitaUtils {

	public static String getRisultatoEsatto(PronosticoRisultato risultato, Partita partita, Partita other) {

		if(!equi(partita, other))
			return null;

		if(other.getCasa().equals(partita.getCasa()) && other.getTrasferta().equals(partita.getTrasferta())) {
			return getRisultatoEsatto(risultato);
		} else {
			return getRisultatoEsattoReversed(risultato);
		}
	}


	
	public static boolean equi(Partita partita, Partita other) {
		
		if(other == null)
			return false;

		if(partita.getCasa() == null)
			return false;
		if(partita.getTrasferta() == null)
			return false;
		if(other.getCasa() == null)
			return false;
		if(other.getTrasferta() == null)
			return false;
		
//		if(!(partita.getTorneo().getType().equals(other.getTorneo().getType()))) {
//			return false;
//		}
		
		if(other.getCasa().equals(partita.getCasa()) && other.getTrasferta().equals(partita.getTrasferta())) {
			return true;
		}
		if(other.getTrasferta().equals(partita.getCasa()) && other.getCasa().equals(partita.getCasa())) {
			return true;
		}
		return false;
	}
	
	public static String getRisultatoEsatto(PronosticoRisultato risultato) {
		return risultato.getGoalHome() + "-" + risultato.getGoalAway();
	}
	
	public static String getRisultatoEsattoReversed(PronosticoRisultato risultato) {
		return risultato.getGoalHome() + "-" + risultato.getGoalAway();
	}
	
	public static Risultato getRisultato(PronosticoRisultato risultato) {
		return (risultato.getGoalHome().intValue()==risultato.getGoalAway().intValue()) ? Risultato.X 
				: (risultato.getGoalHome().intValue()>risultato.getGoalAway().intValue())? Risultato._1 : Risultato._2;
	}
}
