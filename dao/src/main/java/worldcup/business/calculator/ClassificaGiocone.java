package worldcup.business.calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import worldcup.orm.vo.DatiPartitaVO;
import worldcup.orm.vo.PartitaVO;
import worldcup.orm.vo.PronosticoVO;
import worldcup.orm.vo.SubdivisionVO;
import worldcup.orm.vo.TorneoVO;

public class ClassificaGiocone {

	public static Map<PronosticoVO, Integer> getClassifica(TorneoVO torneo) {
		
		Map<PronosticoVO, Integer> map = new HashMap<>();
		for(PronosticoVO pronostico: torneo.getPronostici()) {

			if(!pronostico.getId().equals(torneo.getPronosticoUfficiale().getId())) {
				int punti = getPuntiPronostico(pronostico);
				
				map.put(pronostico, punti);
			}
		}
		
		return map;
	}

	public static Integer getPuntiPronostico(RisultatoPronostico risultatoPronostico) {

		int punti = 0;
		
		for(Entry<SubdivisionVO, Integer> r: risultatoPronostico.getRisultati().entrySet()) {
			punti += r.getKey().getPunteggi().getPuntiPerRisultato() * r.getValue();
		}
		for(Entry<SubdivisionVO, Integer> r: risultatoPronostico.getRisultatiEsatti().entrySet()) {
			punti += r.getKey().getPunteggi().getPuntiPerRisultatoEsatto() * r.getValue();
		}
		for(Entry<SubdivisionVO, Integer> r: risultatoPronostico.getPassaggi().entrySet()) {
			punti += r.getKey().getPunteggi().getPuntiPerPassaggi() * r.getValue();
		}
		for(Entry<SubdivisionVO, Integer> r: risultatoPronostico.getPosizioni().entrySet()) {
			punti += r.getKey().getPunteggi().getPuntiPerPosizione() * r.getValue();
		}
		
		return punti;

	}

	public static Integer getPuntiPronostico(PronosticoVO pronostico) {
		
			RisultatoPronostico risultatoPronostico = getRisultatoPronostico(pronostico.getTorneo(), TorneoUtils.getTorneoPronosticato(pronostico));
			return getPuntiPronostico(risultatoPronostico);
	}

	private static RisultatoPronostico getRisultatoPronostico(TorneoVO torneoUfficiale, TorneoVO torneoPronostico) {

		RisultatoPronostico rp = new RisultatoPronostico();
		for(DatiPartitaVO datiPartitaUfficiale: torneoUfficiale.getPronosticoUfficiale().getDatiPartite()) {
			PartitaVO partitaUfficiale = TorneoUtils.findPartita(datiPartitaUfficiale.getCodicePartita(), torneoUfficiale);
			Optional<DatiPartitaVO> oDatiPartita = TorneoUtils.getDatiPartitaEqui(partitaUfficiale, torneoPronostico);
			if(oDatiPartita.isPresent()) {
				DatiPartitaVO datiPartitaPronostico = oDatiPartita.get();
				PartitaVO partitaPronostico = TorneoUtils.findPartita(datiPartitaPronostico.getCodicePartita(), torneoPronostico);
				if(TorneoUtils.isRisultatoEsatto(partitaUfficiale, datiPartitaUfficiale, partitaPronostico, datiPartitaPronostico)) {
					rp.addRisultatiEsatti(partitaUfficiale.getSubdivision());
				} else if(TorneoUtils.isRisultato1x2(partitaUfficiale, datiPartitaUfficiale, partitaPronostico, datiPartitaPronostico)) {
					rp.addRisultati(partitaUfficiale.getSubdivision());
				}
			}
		}

		//TODO passaggi turno e posizioni
		return rp;
	}
}
