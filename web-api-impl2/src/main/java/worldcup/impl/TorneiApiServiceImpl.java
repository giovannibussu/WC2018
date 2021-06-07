package worldcup.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import worldcup.BadRequestException;
import worldcup.InternalException;
import worldcup.api.TorneiApi;
import worldcup.business.TorneoBD;
import worldcup.business.calculator.ClassificaGiocone;
import worldcup.impl.converter.PartitaConverter;
import worldcup.impl.converter.PronosticoConverter;
import worldcup.impl.converter.PronosticoPartitaConverter;
import worldcup.impl.converter.TorneoConverter;
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
import worldcup.orm.vo.SubdivisionVO;
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
			
			return ResponseEntity.unprocessableEntity().build(); //TODO
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
			
			TorneoVO torneo = this.torneoBD.findByName(idTorneo);

			for(SubdivisionVO s: torneo.getSubdivisions()) {
				for(PartitaVO p: s.getPartite()) {
					if(p.getCodicePartita().equals(idPartita)) {
						return ResponseEntity.ok(PartitaConverter.toRsModel(p, null));
					}
				}
			}
			
			throw new BadRequestException("Partita non trovata");
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
			TorneoVO torneo = this.torneoBD.findByName(idTorneo);

		    List<Pronostico> lst = new ArrayList<>();
			for(PronosticoVO p : torneo.getPronostici()) {
				lst.add(PronosticoConverter.toRsModel(p, ClassificaGiocone.getPuntiPronostico(p, torneo.getPronosticoUfficiale())));
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

	public ResponseEntity<List<PronosticoPartita>> getPronosticiPartita(String idTorneo, String idPartita) {
		if(idTorneo == null) {
			idTorneo = TorneoConfig.ID_TORNEO_DEFAULT;
		}
		
		try {
			
			TorneoVO torneo = this.torneoBD.findByName(idTorneo);

		    List<PronosticoPartita> lst = new ArrayList<>();
			for(PronosticoVO p : torneo.getPronostici()) {
	    		
				DatiPartitaVO dp = p.getDatiPartite().stream().filter(pa -> pa.getPartita().getCodicePartita().equals(idPartita)).findAny().orElseThrow(() -> new BadRequestException("Partita non trovata"));
				lst.add(PronosticoPartitaConverter.toRsModel(dp .getPartita(), dp, p.getGiocatore()));
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
		if(idTorneo == null) {
			idTorneo = TorneoConfig.ID_TORNEO_DEFAULT;
		}
		try {
			TorneoVO torneo = this.torneoBD.findByName(idTorneo);
			return ResponseEntity.ok(TorneoConverter.getTorneo(torneo));
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
			
			TorneoVO torneo = this.torneoBD.findByName(idTorneo);
			List<PartitaVO> matchPerData = new ArrayList<>();
			
			final Date datada = dataDa.toDate();
			final Date dataa = dataA.toDate();
			for(SubdivisionVO s: torneo.getSubdivisions()) {
				 matchPerData.addAll(s.getPartite().stream().filter(p -> p.getData().after(datada) && p.getData().before(dataa))
				.collect(Collectors.toList()));
			}
			
			
			List<Partita> lst = new ArrayList<>();
		    
			int rOffset = offset != null ? offset.intValue() : 0;
			int rLimit = limit != null ? limit: 50;
		    if(matchPerData != null) {
		    	for(int i = rOffset; i < rLimit; i++){
					PartitaVO partitaVO = matchPerData.get(i);
					DatiPartitaVO dp = torneo.getPronosticoUfficiale().getDatiPartite().stream()
							.filter(p -> p.getPartita().getCodicePartita().equals(partitaVO.getCodicePartita()))
							.findAny().orElse(null);
					lst.add(PartitaConverter.toRsModel(partitaVO, dp));
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
			return null; //TODO
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
			
			this.torneoBD.save(dpVO);
			Partita rsModel = PartitaConverter.toRsModel(dpVO.getPartita(),dpVO);
			
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
