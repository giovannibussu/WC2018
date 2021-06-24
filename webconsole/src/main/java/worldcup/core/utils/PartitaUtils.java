package worldcup.core.utils;

import java.util.List;

import worldcup.clients.model.Partita;
import worldcup.clients.model.PronosticoRisultato;
import worldcup.clients.model.Risultato;
import worldcup.clients.model.RisultatoPartita;

public class PartitaUtils {
	
	public static Partita getPartitaFromPronostico(List<Partita> partitaGiocatore, Partita partitaCheck) {

		Partita p = null;
		for (Partita partita : partitaGiocatore) {
			if(equi(partitaCheck, partita)) {
				p = partita;
				break;
			}
		}
		
		return p;
	}
	
	public static String getRisultatoEsatto(RisultatoPartita risultato, Partita partita, Partita other) {

		if(!equi(partita, other))
			return null;

		if(other.getCasa().equals(partita.getCasa()) && other.getTrasferta().equals(partita.getTrasferta())) {
			return getRisultatoEsatto(risultato);
		} else {
			return getRisultatoEsattoReversed(risultato);
		}
	}
	

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
		return risultato.getGoalAway() + "-" + risultato.getGoalHome();
	}
	
	public static Risultato getRisultato(PronosticoRisultato risultato) {
		return (risultato.getGoalHome().intValue()==risultato.getGoalAway().intValue()) ? Risultato.X 
				: (risultato.getGoalHome().intValue()>risultato.getGoalAway().intValue())? Risultato._1 : Risultato._2;
	}
	
	public static String getRisultatoEsatto(RisultatoPartita risultato) {
		return risultato.getGoalCasa() + "-" + risultato.getGoalTrasferta();
	}
	
	public static String getRisultatoEsattoReversed(RisultatoPartita risultato) {
		return risultato.getGoalTrasferta() + "-" + risultato.getGoalCasa();
	}
	
	public static Risultato getRisultato(RisultatoPartita risultato) {
		return (risultato.getGoalCasa().intValue()==risultato.getGoalTrasferta().intValue()) ? Risultato.X 
				: (risultato.getGoalCasa().intValue()>risultato.getGoalTrasferta().intValue())? Risultato._1 : Risultato._2;
	}
	
	public static int getPunti(Partita partita, Partita other) {
		if(equi(partita, other)) {
			RisultatoPartita risultato = other.getRisultato();
			String risultatoOtherString;
			if(other.getCasa().equals(partita.getCasa()) && other.getTrasferta().equals(partita.getTrasferta())) {
				risultatoOtherString = getRisultatoEsatto(risultato);
			} else {
				risultatoOtherString = getRisultatoEsattoReversed(risultato);
			}
			
			String risultatoString = getRisultatoEsatto(partita.getRisultato());
			if(risultatoString.equals(risultatoOtherString)) {
				if(partita.getDescrizione().contains("gironi")) {
					return 3;
				}
				if(partita.getDescrizione().contains("Ottavi")) {
					return 7;
				}
				if(partita.getDescrizione().contains("Quarti")) {
					return 11;
				}
				if(partita.getDescrizione().contains("Semifinale")) {
					return 15;
				}
				if(partita.getDescrizione().contains("Finale")) {
					return 21;
				}
				return 3;
			} else {
				Risultato risultatoEnum = getRisultato(partita.getRisultato());
				Risultato risultatoOtherEnum = getRisultato(other.getRisultato());
				if(risultatoEnum.equals(risultatoOtherEnum)) {
					if(partita.getDescrizione().contains("gironi")) {
						return 1;
					}
					if(partita.getDescrizione().contains("Ottavi")) {
						return 3;
					}
					if(partita.getDescrizione().contains("Quarti")) {
						return 5;
					}
					if(partita.getDescrizione().contains("Semifinale")) {
						return 6;
					}
					if(partita.getDescrizione().contains("Finale")) {
						return 7;
					}
					return 1;
				}
			}
		}
		
		return 0;
	}
	
	public static String getClasseCSS(Partita partita, Partita other) {
		if(equi(partita, other)) {
			RisultatoPartita risultato = other.getRisultato();
			String risultatoOtherString;
			if(other.getCasa().equals(partita.getCasa()) && other.getTrasferta().equals(partita.getTrasferta())) {
				risultatoOtherString = getRisultatoEsatto(risultato);
			} else {
				risultatoOtherString = getRisultatoEsattoReversed(risultato);
			}
			
			String risultatoString = getRisultatoEsatto(partita.getRisultato());
			if(risultatoString.equals(risultatoOtherString)) {
				return "giocatore-pronostico-esatto";
			} else {
				Risultato risultatoEnum = getRisultato(partita.getRisultato());
				Risultato risultatoOtherEnum = getRisultato(other.getRisultato());
				if(risultatoEnum.equals(risultatoOtherEnum)) {
					return "giocatore-pronostico-ok";
				}
			}
		}
		
		return "";
	}
}
