package worldcup.business.calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import worldcup.orm.vo.PartitaVO;
import worldcup.orm.vo.PropertyVO;
import worldcup.orm.vo.SquadraVO;
import worldcup.orm.vo.SubdivisionVO;


public class ComposizioneOttavi {

	public SubdivisionVO getPartite(GironeResult girone, List<SubdivisionVO> subdivisionsInput, SubdivisionVO subdivisionInput) {
		
		Map<String, String> resultMap = getTerze(girone, subdivisionInput);

		Set<PartitaVO> partite = new HashSet<>();
		
		SubdivisionVO subdivision = new SubdivisionVO();
		subdivision.setNome(subdivisionInput.getNome());
		subdivision.setTipo(subdivisionInput.getTipo());
		for(PartitaVO p : subdivision.getPartite()) {
			PartitaVO partita = getPartita(resultMap, girone, p.getCodiceCalcoloCasa(), p.getCodiceCalcoloTrasferta());
			partita.setCodicePartita(p.getCodicePartita());
			partite.add(partita);
		}
		subdivision.setPartite(partite);
		return subdivision;
	}

	private Map<String, String> getTerze(GironeResult girone, SubdivisionVO subdivisionInput) {
		
		if(subdivisionInput.getAlgoritmoTerze()!=null) {		
			// 1. dal gironeResult prendo la classifica delle terze, prendo le prime 4 e ne colleziono i gironi.
			List<String> codici = new ArrayList<String>();
			for(int i = 0; i < 4; i++) {
				codici.add(girone.getClassificaOrizzontale(3).getSquadre().get((i+1)).getGirone());
			}
			
			// 2. la lista dei gironi delle terze lucky ordinata in ordine alfabetico rappresenta la chiave con cui decodifico le 4 terze da opporre
			StringBuffer sb = new StringBuffer();
			
			codici.stream().sorted().forEach(s -> sb.append(s));
	
			String keyTerze = sb.toString();
			
			// 3. decodifico la stringa delle terze in una mappa che associa la terza lucky al girone opponent corrispondente
			Map<String, String> map = new HashMap<>();
			
			String values = subdivisionInput.getAlgoritmoTerze().stream().filter
					(t -> {return keyTerze.equals(t.getNome());}).findAny()
					.orElseThrow(() -> new RuntimeException("Algoritmo terze non trovato per key ["+keyTerze+"]")).getValore();
			for(PropertyVO p: subdivisionInput.getOpponentsTerze()) {
				map.put(p.getNome(), values.charAt(Integer.parseInt(p.getValore())) + "");	
			}

			return map;

		} else {
			return null;
		}
	}
	
	private PartitaVO getPartita(Map<String, String> mapTerze, GironeResult girone, String codCasa, String codTrasferta) {
		PartitaVO datiPartita = new PartitaVO();
		int posizioneCasa = Integer.parseInt(codCasa.charAt(0) + "");
		String gironeCasa = codCasa.charAt(1) + "";
		
		SquadraVO squadraCasa = girone.getClassificaVerticale(gironeCasa).getSquadre().get(posizioneCasa).getSquadra();
		
		datiPartita.setCasa(squadraCasa);

		String gironeTrasferta = null;
		int posizioneTrasferta = -1;
		if(codTrasferta.equals("3")) {
			posizioneTrasferta = 3;
			gironeTrasferta = mapTerze.get(gironeCasa);
		} else {
			posizioneTrasferta = Integer.parseInt(codTrasferta.charAt(0) + "");
			gironeTrasferta = codTrasferta.charAt(1) + "";
		}
		
		SquadraVO squadraTrasferta = girone.getClassificaVerticale(gironeTrasferta).getSquadre().get(posizioneTrasferta).getSquadra();
		datiPartita.setTrasferta(squadraTrasferta);
		
		return datiPartita;
		
	}
}
