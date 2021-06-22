package worldcup.business.calculator;

import worldcup.orm.vo.DatiPartitaVO;
import worldcup.orm.vo.PartitaVO;
import worldcup.orm.vo.PronosticoVO;
import worldcup.orm.vo.SubdivisionVO;

public class KnockoutCalculator {

	public KnockoutResult getResult(SubdivisionVO knockout, PronosticoVO pronostico) {
		KnockoutResult result = new KnockoutResult();
		
		
		for(PartitaVO partita: knockout.getPartite()) {
			record(partita, pronostico, result, knockout.getNome());
		}
			
		return result;
	}

	private void record(PartitaVO partita, PronosticoVO pronostico, KnockoutResult result, String nome) {
		DatiPartitaVO dp = TorneoUtils.getDatiPartita(partita.getCodicePartita(), pronostico);
	
		if(TorneoUtils.isGiocata(dp)) {
			
			String risultatoEsatto = TorneoUtils.getRisultato1x2(dp, false);
			
			if(risultatoEsatto.equals("X")) {
				throw new RuntimeException("Pareggio non ammesso in " + nome);
			}
			
			if(risultatoEsatto.equals("1")) {
				result.setWinner(partita.getCodicePartita(), partita.getCasa());
				result.setLoser(partita.getCodicePartita(), partita.getTrasferta());
			} else if(risultatoEsatto.equals("2")) {
				result.setLoser(partita.getCodicePartita(), partita.getCasa());
				result.setWinner(partita.getCodicePartita(), partita.getTrasferta());
			}
		}
	}

}
