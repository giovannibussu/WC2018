package worldcup.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.deser.DataFormatReaders.Match;

import worldcup.InternalException;
import worldcup.api.TorneiApi;
import worldcup.business.TorneoBD;
import worldcup.business.calculator.ClassificaGiocone;
import worldcup.impl.converter.PartitaConverter;
import worldcup.impl.converter.PronosticoConverter;
import worldcup.impl.utils.TorneoAuthorizationManager;
import worldcup.impl.utils.TorneoConfig;
import worldcup.model.Grafico;
import worldcup.model.Partita;
import worldcup.model.Pronostico;
import worldcup.model.PronosticoPartita;
import worldcup.model.RisultatoPartita;
import worldcup.model.TipoDistribuzione;
import worldcup.model.Torneo;
import worldcup.orm.vo.DatiPartitaVO;
import worldcup.orm.vo.PartitaVO;
import worldcup.orm.vo.PronosticoVO;
import worldcup.orm.vo.TorneoVO;

@RestController
public class TorneiApiServiceImpl implements TorneiApi {

	private Logger logger = LoggerFactory.getLogger(TorneiApiServiceImpl.class);

	private List<String> categorieAutorizzate = null;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private TorneoBD torneoBD;

	public TorneiApiServiceImpl() {
		this.categorieAutorizzate = new ArrayList<>();
		this.categorieAutorizzate.add("Link");
	}

	public ResponseEntity<List<Pronostico>> getClassifica(String idTorneo, String categoria) {
		if(idTorneo == null) {
			idTorneo = TorneoConfig.ID_TORNEO_DEFAULT;
		}
		
		try {
			
			TorneoVO torneo = torneoBD.findByName(idTorneo);
			
			Map<Integer, PronosticoVO> map = ClassificaGiocone.getClassifica(torneo);
			
			
			List<Pronostico> lst = new ArrayList<Pronostico>();
			
			map.keySet().stream().sorted(Comparator.reverseOrder()).forEach( k -> {
			
				lst.add(PronosticoConverter.toRsModel(map.get(k), k));

			});

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
		if(idTorneo == null) {
			idTorneo = TorneoConfig.ID_TORNEO_DEFAULT;
		}
		
		try {
			
			return ResponseEntity.unprocessableEntity().build();
		} catch(RuntimeException e) {
			this.logger.error("Invocazione terminata con errore '4xx': " +e.getMessage(),e);
			throw e;
		}
		catch(Throwable e) {
			this.logger.error("Invocazione terminata con errore: " +e.getMessage(),e);
			throw new InternalException(e);
		}
	}

	public ResponseEntity<Partita> getPartita(String idTorneo, String idPartita) {
		if(idTorneo == null) {
			idTorneo = TorneoConfig.ID_TORNEO_DEFAULT;
		}
		
		try {
			return ResponseEntity.unprocessableEntity().build();
		} catch(RuntimeException e) {
			this.logger.error("Invocazione terminata con errore '4xx': " +e.getMessage(),e);
			throw e;
		}
		catch(Throwable e) {
			this.logger.error("Invocazione terminata con errore: " +e.getMessage(),e);
			throw new InternalException(e);
		}
	}

	public ResponseEntity<List<Pronostico>> getPronostici(String idTorneo) {
		if(idTorneo == null) {
			idTorneo = TorneoConfig.ID_TORNEO_DEFAULT;
		}
		
		try {
//			Gioco gioco = new Gioco();
//			List<worldcup.core.Pronostico> listaPronostici = gioco.getListaPronostici();
//			
//			List<Pronostico> lst = new ArrayList<>();
//
//			if(idTorneo != null && this.categorieAutorizzate.contains(idTorneo)) {
//				for (worldcup.core.Pronostico pronostico : listaPronostici) {
//					if(idTorneo.equals(pronostico.getPlayer().getCategoria()))
//						lst.add(PronosticoConverter.toRsModel(pronostico, 0));
//				}
//				
//			} else {
//				for (worldcup.core.Pronostico pronostico : listaPronostici) {
//					lst.add(PronosticoConverter.toRsModel(pronostico, 0));
//				}
//			}

			return ResponseEntity.unprocessableEntity().build();
		} catch(RuntimeException e) {
			this.logger.error("Invocazione terminata con errore '4xx': " +e.getMessage(),e);
			throw e;
		}
		catch(Throwable e) {
			this.logger.error("Invocazione terminata con errore: " +e.getMessage(),e);
			throw new InternalException(e);
		}
	}

	public ResponseEntity<List<PronosticoPartita>> getPronosticiPartita(String idTorneo, String idPartita) {
		if(idTorneo == null) {
			idTorneo = TorneoConfig.ID_TORNEO_DEFAULT;
		}
		
		try {
//			Gioco gioco = new Gioco();
//		    Match match = gioco.getMatch(idPartita);
//		    
//		    Map<Player, Match> listaPronosticiMatch = gioco.pronosticiPerMatch(match);
//		    
//		    List<PronosticoPartita> lst = new ArrayList<>();
//		    
//		    if(listaPronosticiMatch != null) {
//		    	for(Player p: listaPronosticiMatch.keySet()){
//					lst.add(PronosticoPartitaConverter.toRsModel(listaPronosticiMatch.get(p), p));
//				}
//		    }
//		    
//			return ResponseEntity.ok(lst);
			return ResponseEntity.unprocessableEntity().build();
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
		if(idTorneo == null) {
			idTorneo = TorneoConfig.ID_TORNEO_DEFAULT;
		}
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

	public ResponseEntity<List<Partita>> listPartite(String idTorneo, Integer limit, Long offset, DateTime dataDa,	DateTime dataA) {
		if(idTorneo == null) {
			idTorneo = TorneoConfig.ID_TORNEO_DEFAULT;
		}
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
				nowCal.add(Calendar.DATE, 6);
				Date tomorrow= nowCal.getTime();
				dataA = new DateTime(tomorrow.getTime());
			}
			
			TorneoVO torneo = torneoBD.findByName(idTorneo);
			List<PartitaVO> matchPerData = new ArrayList<>();
//			for(s: torneo.getSubdivisions()) {}
			
			
			List<Partita> lst = new ArrayList<>();
		    
		    if(matchPerData != null) {
//		    	for(Match match: matchPerData){
//					lst.add(PartitaConverter.toRsModel(match));
//				}
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

	public ResponseEntity<Partita> updateRisultatoPartita(String idTorneo, String idPartita, RisultatoPartita risultatoPartita) {
		if(idTorneo == null) {
			idTorneo = TorneoConfig.ID_TORNEO_DEFAULT;
		}
		try {
			TorneoAuthorizationManager.autorizza(this.logger, this.request);
			
			TorneoVO torneo = torneoBD.findByName(idTorneo);
			
			final String codPartita = idPartita;
			DatiPartitaVO dpVO = torneo.getPronosticoUfficiale().getDatiPartite().stream().filter(dp -> {return dp.getPartita().getCodicePartita().equals(codPartita);})
					.findAny().orElseThrow(() -> new RuntimeException("Partita non trovata"));

			dpVO.setGoalCasa(risultatoPartita.getGoalCasa());

			dpVO.setGoalTrasferta(risultatoPartita.getGoalTrasferta());
			Partita rsModel = PartitaConverter.toRsModel(dpVO);
			
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
