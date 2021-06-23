package worldcup.business.calculator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import worldcup.business.calculator.TorneoUtils.CasaTrasfertaEnum;
import worldcup.orm.vo.DatiPartitaVO;
import worldcup.orm.vo.PartitaVO;
import worldcup.orm.vo.PronosticoVO;
import worldcup.orm.vo.SquadraVO;
import worldcup.orm.vo.SubdivisionVO;

public class GironeCalculator implements IGironeCalculator {

	@Override
	public GironeResult getResult(Collection<SubdivisionVO> gironi, PronosticoVO pronostico, RegoleGirone conf) {
		GironeResult result = new GironeResult();
		
		
		for(SubdivisionVO girone: gironi) {
			Map<String, GironePerformance> map = new HashMap<>();
			for(PartitaVO partita: girone.getPartite()) {
				record(partita, pronostico, map, girone.getNome());
			}
			
			Classifica classifica = conf.getClassificaVerticale(map.values());
			result.setClassificaVerticale(girone.getNome(), classifica);
		}

		Map<Integer, Collection<GironePerformance>> map = new HashMap<>();
		for(SubdivisionVO girone: gironi) {
			for(int i = 0; i < girone.getSquadre().size(); i++) {
				int pos = i+1;
				if(!map.containsKey(pos)) {
					Collection<GironePerformance> lst = new ArrayList<>();
					map.put(pos, lst);
				}
				GironePerformance p = result.getClassificaVerticale(girone.getNome()).getSquadre().get(i+1);
				map.get(pos).add(p);
			}
		}
		

		for(Entry<Integer, Collection<GironePerformance>> e: map.entrySet()) {
			if(e.getKey().equals(3))
				result.setClassificaOrizzontale(e.getKey(), conf.getClassificaOrizzontale(e.getValue()));
		}

		return result;
	}

	private void record(PartitaVO partita, PronosticoVO pronostico, Map<String, GironePerformance> map, String girone) {
		DatiPartitaVO dp = TorneoUtils.getDatiPartita(partita.getCodicePartita(), pronostico);
	
		if(TorneoUtils.isGiocata(dp)) {
			
			for(CasaTrasfertaEnum cte:CasaTrasfertaEnum.values()) {
				SquadraVO squadra = TorneoUtils.getSquadra(partita, dp, cte);
				SquadraVO squadraContro = TorneoUtils.getSquadraContro(partita, dp, cte);
				GironePerformance performance = null;
				if(!map.containsKey(squadra.getNome())) {
					performance = new GironePerformance();
					performance.setSquadra(squadra);
					performance.setGirone(girone);
					map.put(squadra.getNome(), performance);
				} else {
					performance = map.get(squadra.getNome());
				}
				
				GironeSingleMatchPerformance singlep = new GironeSingleMatchPerformance();
				singlep.setPunti(TorneoUtils.getPunti(dp, cte));
				singlep.setVittorie(TorneoUtils.getVittoria(dp, cte));
				singlep.setGoalFatti(TorneoUtils.getGoalFatti(dp, cte));
				singlep.setGoalSubiti(TorneoUtils.getGoalSubiti(dp, cte));

				performance.getPerformances().put(squadraContro.getNome(), singlep);
			}
		}
	}

}
