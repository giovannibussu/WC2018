package worldcup.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import worldcup.api.TorneiApi;
import worldcup.core.Gioco;
import worldcup.impl.converter.PronosticoConverter;
import worldcup.main.InternalException;
import worldcup.model.Grafico;
import worldcup.model.Partita;
import worldcup.model.Pronostico;
import worldcup.model.PronosticoPartita;
import worldcup.model.RisultatoPartita;
import worldcup.model.TipoDistribuzione;
import worldcup.model.Torneo;

@RestController
public class TorneiApiServiceImpl implements TorneiApi {

	private Logger logger = LoggerFactory.getLogger(TorneiApiServiceImpl.class);
	
	private List<String> categorieAutorizzate = null;
	
	public TorneiApiServiceImpl() {
		this.categorieAutorizzate = new ArrayList<>();
		this.categorieAutorizzate.add("Link");
	}

	public ResponseEntity<List<Pronostico>> getClassifica(String idTorneo) {
		try {
			Gioco gioco = new Gioco();
			Map<worldcup.core.Pronostico, Integer> classifica = gioco.getClassifica();
			List<Pronostico> lst = new ArrayList<>();

			if(idTorneo != null && this.categorieAutorizzate.contains(idTorneo)) {
				// Gestire filtro per visualizzare solo gli utenti indicati
				// da utilizzare solo per vedere categorie di utenti autorizzate
				Map<worldcup.core.Pronostico, Integer> toReturn = new HashMap<>();
				for(worldcup.core.Pronostico pronostico: classifica.keySet()) {
					if(idTorneo.equals(pronostico.getPlayer().getCategoria()))
						toReturn.put(pronostico, classifica.get(pronostico));
				}
				
				LinkedHashMap<worldcup.core.Pronostico, Integer> reverseSortedMap = new LinkedHashMap<>();
				
				//Use Comparator.reverseOrder() for reverse ordering
				toReturn.entrySet()
				    .stream()
				    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				    .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
				
				for(worldcup.core.Pronostico pronostico: reverseSortedMap.keySet()){
              		Integer punteggioGiocatore = reverseSortedMap.get(pronostico);
					lst.add(PronosticoConverter.toRsModel(pronostico, punteggioGiocatore));
				}
			} else {
				for(worldcup.core.Pronostico pronostico: classifica.keySet()){
              		Integer punteggioGiocatore = classifica.get(pronostico);
					lst.add(PronosticoConverter.toRsModel(pronostico, punteggioGiocatore));
				}
			}
			
			return ResponseEntity.ok(lst);
		} catch(RuntimeException e) {
			this.logger.error("Invocazione terminata con errore '4xx': " +e.getMessage(),e);
			throw e;
		}
		catch(Throwable e) {
			this.logger.error("Invocazione terminata con errore: " +e.getMessage(),e);
			throw new InternalException(e);
		}
	}

	public ResponseEntity<Grafico> getDistribuzionePartita(String idTorneo, String idPartita, TipoDistribuzione tipo) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResponseEntity<Partita> getPartita(String idTorneo, String idPartita) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResponseEntity<List<Pronostico>> getPronostici(String idTorneo) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResponseEntity<List<PronosticoPartita>> getPronosticiPartita(String idTorneo, String idPartita) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResponseEntity<Torneo> getTorneo(String idTorneo) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResponseEntity<List<Partita>> listPartite(String idTorneo, Integer limit, Long offset, DateTime dataDa,
			DateTime dataA) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResponseEntity<List<Torneo>> listTornei(Integer offset) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResponseEntity<Partita> updateRisultatoPartita(String idTorneo, String idPartita,
			RisultatoPartita risultatoPartita) {
		// TODO Auto-generated method stub
		return null;
	}
}
