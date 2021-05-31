package worldcup.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
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
import worldcup.impl.converter.GraficoConverter;
import worldcup.impl.converter.PartitaConverter;
import worldcup.impl.converter.PronosticoConverter;
import worldcup.impl.converter.PronosticoPartitaConverter;
import worldcup.InternalException;
import worldcup.model.Grafico;
import worldcup.model.Partita;
import worldcup.model.Pronostico;
import worldcup.model.PronosticoPartita;
import worldcup.model.RisultatoPartita;
import worldcup.model.TipoDistribuzione;
import worldcup.model.Torneo;

import worldcup.core.model.Match;
import worldcup.core.model.Player;

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

	// TODO identificativo torneo a cosa serve?
	public ResponseEntity<Grafico> getDistribuzionePartita(String idTorneo, String idPartita, TipoDistribuzione tipo) {
		try {
			Gioco gioco = new Gioco();
		    Match match = gioco.getMatch(idPartita);
		    
		    if(tipo == null) {
		    	tipo = TipoDistribuzione._1X2;
		    }
			
		    worldcup.core.Grafico grafico = null;
		    switch (tipo) {
			case RISULTATO:
				grafico = gioco.distribuzionePronosticiPerMatchRisultatoEsatto(match);
				break;
			case _1X2:
				grafico = gioco.distribuzionePronosticiPerMatchRisultato(match);
				break;
			}
		    
		    Grafico rsModel = GraficoConverter.toRsModel(grafico);
			
			return ResponseEntity.ok(rsModel);
		} catch(RuntimeException e) {
			this.logger.error("Invocazione terminata con errore '4xx': " +e.getMessage(),e);
			throw e;
		}
		catch(Throwable e) {
			this.logger.error("Invocazione terminata con errore: " +e.getMessage(),e);
			throw new InternalException(e);
		}
	}

	// TODO identificativo torneo a cosa serve?
	public ResponseEntity<Partita> getPartita(String idTorneo, String idPartita) {
		try {
			Gioco gioco = new Gioco();
		    Match match = gioco.getMatch(idPartita);
		    
		    Partita rsModel = PartitaConverter.toRsModel(match);
			
			return ResponseEntity.ok(rsModel);
		} catch(RuntimeException e) {
			this.logger.error("Invocazione terminata con errore '4xx': " +e.getMessage(),e);
			throw e;
		}
		catch(Throwable e) {
			this.logger.error("Invocazione terminata con errore: " +e.getMessage(),e);
			throw new InternalException(e);
		}
	}

	// TODO identificativo torneo a cosa serve?
	public ResponseEntity<List<Pronostico>> getPronostici(String idTorneo) {
		try {
			Gioco gioco = new Gioco();
			List<worldcup.core.Pronostico> listaPronostici = gioco.getListaPronostici();
			
			List<Pronostico> lst = new ArrayList<>();

			if(idTorneo != null && this.categorieAutorizzate.contains(idTorneo)) {
				for (worldcup.core.Pronostico pronostico : listaPronostici) {
					if(idTorneo.equals(pronostico.getPlayer().getCategoria()))
						lst.add(PronosticoConverter.toRsModel(pronostico, 0));
				}
				
			} else {
				for (worldcup.core.Pronostico pronostico : listaPronostici) {
					lst.add(PronosticoConverter.toRsModel(pronostico, 0));
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

	// TODO identificativo torneo a cosa serve?
	public ResponseEntity<List<PronosticoPartita>> getPronosticiPartita(String idTorneo, String idPartita) {
		try {
			Gioco gioco = new Gioco();
		    Match match = gioco.getMatch(idPartita);
		    
		    Map<Player, Match> listaPronosticiMatch = gioco.pronosticiPerMatch(match);
		    
		    List<PronosticoPartita> lst = new ArrayList<>();
		    
		    if(listaPronosticiMatch != null) {
		    	for(Player p: listaPronosticiMatch.keySet()){
					lst.add(PronosticoPartitaConverter.toRsModel(listaPronosticiMatch.get(p), p));
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

	public ResponseEntity<Torneo> getTorneo(String idTorneo) {
		try {
			return null;
		} catch(RuntimeException e) {
			this.logger.error("Invocazione terminata con errore '4xx': " +e.getMessage(),e);
			throw e;
		}
		catch(Throwable e) {
			this.logger.error("Invocazione terminata con errore: " +e.getMessage(),e);
			throw new InternalException(e);
		}
	}

	// TODO identificativo torneo a cosa serve?
	public ResponseEntity<List<Partita>> listPartite(String idTorneo, Integer limit, Long offset, DateTime dataDa,	DateTime dataA) {
		try {
			if(dataDa == null) {
				Calendar nowCal = new GregorianCalendar();
				nowCal.set(Calendar.HOUR_OF_DAY, 0);
				nowCal.set(Calendar.MINUTE, 0);
				dataDa = new DateTime(nowCal.getTime());
			}

			if(dataA == null) {
				Calendar nowCal = new GregorianCalendar();
				nowCal.set(Calendar.HOUR_OF_DAY, 0);
				nowCal.set(Calendar.MINUTE, 0);
				nowCal.add(Calendar.DATE, 2);
				Date tomorrow= nowCal.getTime();
				dataA = new DateTime(tomorrow.getTime());
			}
			
			Gioco gioco = new Gioco();
			
			List<Match> matchPerData = gioco.matchPerData(dataDa.toDate(), dataA.toDate());
			
			
			List<Partita> lst = new ArrayList<>();
		    
		    if(matchPerData != null) {
		    	for(Match match: matchPerData){
					lst.add(PartitaConverter.toRsModel(match));
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

	public ResponseEntity<List<Torneo>> listTornei(Integer offset) {
		try {
			return null;
		} catch(RuntimeException e) {
			this.logger.error("Invocazione terminata con errore '4xx': " +e.getMessage(),e);
			throw e;
		}
		catch(Throwable e) {
			this.logger.error("Invocazione terminata con errore: " +e.getMessage(),e);
			throw new InternalException(e);
		}
	}

	// TODO identificativo torneo a cosa serve?
	public ResponseEntity<Partita> updateRisultatoPartita(String idTorneo, String idPartita, RisultatoPartita risultatoPartita) {
		try {
			
			Gioco gioco = new Gioco();
			
			Match match = gioco.getMatch(idPartita);
			
			gioco.setResult(match, risultatoPartita.getGoalCasa(), risultatoPartita.getGoalTrasferta());
			
			match = gioco.getMatch(idPartita);
			
			Partita rsModel = PartitaConverter.toRsModel(match);
			
			return ResponseEntity.ok(rsModel);
		} catch(RuntimeException e) {
			this.logger.error("Invocazione terminata con errore '4xx': " +e.getMessage(),e);
			throw e;
		}
		catch(Throwable e) {
			this.logger.error("Invocazione terminata con errore: " +e.getMessage(),e);
			throw new InternalException(e);
		}
	}
}
