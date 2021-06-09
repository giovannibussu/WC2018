package worldcup.business.calculator;

import java.util.Optional;

import worldcup.orm.vo.DatiPartitaVO;
import worldcup.orm.vo.PronosticoVO;

public class TorneoUtils {

	public enum CasaTrasfertaEnum {CASA, TRASFERTA}

//	public static DatiPartitaVO getDatiPartita(PartitaVO partita, PronosticoVO pronostico) {
//		return pronostico.getDatiPartite().stream().filter(dp -> dp.getPartita().getCodicePartita().equals(partita.getCodicePartita()))
//				.findAny().orElseThrow(() -> new RuntimeException("Partita ["+partita.getCodicePartita()+"] non trovata nel pronostico ["+pronostico.getIdPronostico()+"]"));
//		
//	}
//
	
	public static DatiPartitaVO getDatiPartita(String idPartita, PronosticoVO p) {
		return getOptDatiPartita(idPartita, p).orElseThrow(() -> new RuntimeException("Partita non trovata"));
	}

	public static Optional<DatiPartitaVO> getOptDatiPartita(String idPartita, PronosticoVO p) {
		return p.getDatiPartite().stream().filter(pa -> pa.getCodicePartita().equals(idPartita)).findAny();
	}

	public static boolean isCasa(CasaTrasfertaEnum casaTrasferta) {
		return casaTrasferta.equals(CasaTrasfertaEnum.CASA);
	}

	public static boolean isGiocata(DatiPartitaVO dati) {
//		return isGiocabile(dati) &&
//				dati.getGoalCasa()!= null && 
//						dati.getGoalTrasferta()!= null;
		return true;
	}

	public static boolean isGiocabile(DatiPartitaVO dati) {
		return dati!= null;
	}
	
	public static Integer getGoalFatti(DatiPartitaVO dati, CasaTrasfertaEnum casaTrasferta) {
		if(!isGiocata(dati)) return null;
		if(isCasa(casaTrasferta)) {
			return dati.getGoalTrasferta();
		} else {
			return dati.getGoalCasa();
		}
	}


	public static Integer getGoalSubiti(DatiPartitaVO dati, CasaTrasfertaEnum casaTrasferta) {
		if(!isGiocata(dati)) return null;
		if(isCasa(casaTrasferta)) {
			return dati.getGoalCasa();
		} else {
			return dati.getGoalTrasferta();
		}
	}

	public static Integer getPunti(DatiPartitaVO dati, CasaTrasfertaEnum casaTrasferta) {
		if(!isGiocata(dati)) return null;
		if(isCasa(casaTrasferta)) {
			return getPunti(dati.getGoalCasa(),dati.getGoalTrasferta());
		} else {
			return getPunti(dati.getGoalTrasferta(),dati.getGoalCasa());
		}
	}

	public static String getRisultatoEsatto(DatiPartitaVO dati) {
		return dati.getGoalCasa() + "-" + dati.getGoalTrasferta();
	}

	public static String getRisultato1x2(DatiPartitaVO dati) {

		if(dati.getGoalCasa() > dati.getGoalTrasferta()) {
			return "1";
		} else if(dati.getGoalTrasferta() > dati.getGoalCasa()) {
			return "2";
		} else {
			return "X";
		}
	}

	private static int getPunti(int goal1, int goal2) {
		if(goal1 == goal2) return 1;
		if(goal1 > goal2) return 3;
		return 0;
		
	}
}
