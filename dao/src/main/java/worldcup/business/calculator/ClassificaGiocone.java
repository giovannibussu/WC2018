package worldcup.business.calculator;

import java.util.HashMap;
import java.util.Map;

import worldcup.orm.vo.DatiPartitaVO;
import worldcup.orm.vo.PronosticoVO;
import worldcup.orm.vo.TorneoVO;

public class ClassificaGiocone {

	private static int coefficente1x2 = 1;
	private static int coefficenteRisultatiEsatti = 3;
	public static Map<Integer, PronosticoVO> getClassifica(TorneoVO torneo) {
		
		Map<Integer, PronosticoVO> map = new HashMap<>();
		for(PronosticoVO pronostico: torneo.getPronostici()) {

			int punti = getPuntiPronostico(pronostico, torneo.getPronosticoUfficiale());
			
			map.put(punti, pronostico);
		}
		
		return map;
	}

	public static Integer getPuntiPronostico(PronosticoVO pronostico, PronosticoVO ufficiale) {
		
			RisultatoPronostico risultatoPronostico = getRisultatoPronostico(pronostico, ufficiale);

			int punti = (risultatoPronostico.getRisultati1x2() * coefficente1x2) +
					(risultatoPronostico.getRisultati1x2() * coefficenteRisultatiEsatti);
			
		return punti;
	}

	private static RisultatoPronostico getRisultatoPronostico(PronosticoVO pronostico,
			PronosticoVO pronosticoUfficiale) {

		RisultatoPronostico rp = new RisultatoPronostico();
		for(DatiPartitaVO datiPartitaUfficiale: pronosticoUfficiale.getDatiPartite()) {
			DatiPartitaVO datiPartita = TorneoUtils.getDatiPartita(datiPartitaUfficiale, pronostico);
			if(TorneoUtils.getRisultatoEsatto(datiPartita).equals(TorneoUtils.getRisultatoEsatto(datiPartitaUfficiale))) {
				rp.addRisultati1x2();
				rp.addRisultatiEsatti();
			} else if(TorneoUtils.getRisultato1x2(datiPartita).equals(TorneoUtils.getRisultato1x2(datiPartitaUfficiale))) {
				rp.addRisultati1x2();
			}
		}
		
		return rp;
	}
}
