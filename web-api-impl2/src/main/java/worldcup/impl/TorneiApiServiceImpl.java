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
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.format.Formatter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import worldcup.BadRequestException;
import worldcup.InternalException;
import worldcup.NotFoundException;
import worldcup.api.TorneiApi;
import worldcup.business.TorneoBD;
import worldcup.business.calculator.ClassificaGiocone;
import worldcup.business.calculator.Distribuzione;
import worldcup.business.calculator.TorneoUtils;
import worldcup.impl.converter.GraficoConverter;
import worldcup.impl.converter.PartitaConverter;
import worldcup.impl.converter.PronosticoConverter;
import worldcup.impl.converter.PronosticoPartitaConverter;
import worldcup.impl.converter.TorneoConverter;
import worldcup.impl.utils.TorneoAuthorizationManager;
import worldcup.model.Grafico;
import worldcup.model.Partita;
import worldcup.model.Pronostico;
import worldcup.model.PronosticoPartita;
import worldcup.model.RisultatoPartita;
import worldcup.model.TipoDistribuzione;
import worldcup.model.Torneo;
import worldcup.orm.vo.DatiPartitaVO;
import worldcup.orm.vo.GiocatoreVO;
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

	@Autowired
	private TorneoAuthorizationManager torneoAuthorizationManager;

	@Autowired
	private Formatter<DateTime> formatter;
	

	public TorneiApiServiceImpl() {
		this.categorieAutorizzate = new ArrayList<>();
		this.categorieAutorizzate.add("Link");
	}

	public ResponseEntity<List<Pronostico>> getClassifica(final String idTorneo, String categoria) {
		try {

			return this.torneoBD.runTransaction(() -> {

				TorneoVO torneo = this.torneoBD.findByName(idTorneo);

				Map<PronosticoVO, Integer> map = ClassificaGiocone.getClassifica(torneo);


				List<Pronostico> lst = new ArrayList<Pronostico>();

				map.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.filter(e -> categoria == null || categoria.equals(e.getKey().getGiocatore().getTags()))
				.forEach( k -> {

					lst.add(PronosticoConverter.toRsModel(k.getKey(), k.getValue()));

				});

				return ResponseEntity.ok(lst);
			});
		} catch(RuntimeException e) {
			this.logger.error("Invocazione terminata con errore '4xx': " +e.getMessage(),e);
			throw e;
		}
		catch(Throwable e) {
			this.logger.error("Invocazione terminata con errore: " +e.getMessage(),e);
			throw new InternalException(e);
		}
	}

	public ResponseEntity<Grafico> getDistribuzionePartita(final String idTorneo, String idPartita, TipoDistribuzione tipo) {
		try {

			return this.torneoBD.runTransaction(() -> {
				TorneoVO torneo = this.torneoBD.findByName(idTorneo);
				List<Distribuzione> distr = TorneoUtils.getDistribuzione(torneo, idPartita, tipo.equals(TipoDistribuzione._1X2));
				String titolo =""; // lasciare vuoti
				String sottotitolo = ""; // lasciare vuoti
				return ResponseEntity.ok(GraficoConverter.toRsModelGrafico(distr, titolo, sottotitolo));
			});
		} catch(RuntimeException e) {
			this.logger.error("Invocazione terminata con errore '4xx': " +e.getMessage(),e);
			throw e;
		}
		catch(Throwable e) {
			this.logger.error("Invocazione terminata con errore: " +e.getMessage(),e);
			throw new InternalException(e);
		}
	}

	public ResponseEntity<Partita> getPartita(final String idTorneo, String idPartita) {
		try {
			return this.torneoBD.runTransaction(() -> {


				TorneoVO torneo = this.torneoBD.findByName(idTorneo);

				for(SubdivisionVO s: torneo.getSubdivisions()) {
					for(PartitaVO p: s.getPartite()) {
						if(p.getCodicePartita().equals(idPartita)) {
							return ResponseEntity.ok(PartitaConverter.toRsModel(p, null, formatter));
						}
					}
				}

				throw new BadRequestException("Partita non trovata");
			});
		} catch(RuntimeException e) {
			this.logger.error("Invocazione terminata con errore '4xx': " +e.getMessage(),e);
			throw e;
		}
		catch(Throwable e) {
			this.logger.error("Invocazione terminata con errore: " +e.getMessage(),e);
			throw new InternalException(e);
		}
	}

	public ResponseEntity<List<Pronostico>> getPronostici(final String idTorneo) {
		return this.torneoBD.runTransaction(() -> {

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
		});
	}


	public ResponseEntity<List<PronosticoPartita>> getPronosticiPartita(String idTorneo, String idPartita) {
		try {
			return this.torneoBD.runTransaction(() -> {

			TorneoVO torneo = this.torneoBD.findByName(idTorneo);

			List<PronosticoPartita> lst = new ArrayList<>();
			for(PronosticoVO p : torneo.getPronostici()) {

				DatiPartitaVO dp = TorneoUtils.getDatiPartita(idPartita, p);
				PartitaVO partita = TorneoUtils.findPartita(idPartita, torneo);
				lst.add(PronosticoPartitaConverter.toRsModel(partita, dp, p.getGiocatore(), formatter));
			}

			return ResponseEntity.ok(lst);
			});
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

	public ResponseEntity<List<Partita>> listPartite(final String idTorneo, Integer limit, Long offset, DateTime dataDa, DateTime dataA, Boolean daGiocare) {
		try {

			return this.torneoBD.runTransaction(() -> {
				final Date datada;
				final Date dataa;
				if(dataDa == null) {
					Calendar nowCal = new GregorianCalendar();
					nowCal.set(Calendar.HOUR_OF_DAY, 0);
					nowCal.set(Calendar.MINUTE, 0);
					datada = new DateTime(nowCal.getTime()).toDate();
				} else {
					datada = dataDa.toDate();
				}

				if(dataA == null) {
					Calendar nowCal = new GregorianCalendar();
					nowCal.set(Calendar.HOUR_OF_DAY, 0);
					nowCal.set(Calendar.MINUTE, 0);
					nowCal.add(Calendar.DATE, 6);
					Date tomorrow= nowCal.getTime();
					dataa = new DateTime(tomorrow.getTime()).toDate();
				} else {
					dataa = dataA.toDate();
				}
				TorneoVO torneo = this.torneoBD.findByName(idTorneo);
				List<PartitaVO> matchPerData = new ArrayList<>();

				for(SubdivisionVO s: torneo.getSubdivisions()) {
					matchPerData.addAll(s.getPartite().stream()
							.filter(p -> p.getData().after(datada) && p.getData().before(dataa) 
									&& (daGiocare == null || TorneoUtils.daGiocare(torneo, p.getCodicePartita()) == daGiocare))
							.collect(Collectors.toList()));
					
				}

				matchPerData = matchPerData.stream().sorted(new Comparator<PartitaVO>() {

					@Override
					public int compare(PartitaVO o1, PartitaVO o2) {
						return o1.getData().compareTo(o2.getData());
					}}).collect(Collectors.toList());


				List<Partita> lst = new ArrayList<>();

				int rOffset = offset != null ? offset.intValue() : 0;
				int rLimit = limit != null ? limit: 50;
				if(matchPerData != null) {
					for(int i = rOffset; i < rLimit && i < matchPerData.size(); i++){
						PartitaVO partitaVO = matchPerData.get(i);
						DatiPartitaVO dp = torneo.getPronosticoUfficiale().getDatiPartite().stream()
								.filter(p -> p.getCodicePartita().equals(partitaVO.getCodicePartita()))
								.findAny().orElse(null);
						lst.add(PartitaConverter.toRsModel(partitaVO, dp, formatter));
					}
				}

				return ResponseEntity.ok(lst);
			});
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

	public ResponseEntity<Partita> updateRisultatoPartita(final String idTorneo, final String idPartita, RisultatoPartita risultatoPartita) {
		return this.torneoBD.runTransaction(() -> {
			try {
				this.torneoAuthorizationManager.autorizza(this.logger, this.request);

				return this.torneoBD.runTransaction(() -> {
					TorneoVO torneo = this.torneoBD.findByName(idTorneo);

					DatiPartitaVO dpVO = TorneoUtils.getOptDatiPartita(idPartita, torneo.getPronosticoUfficiale()).orElseGet(() -> {
						DatiPartitaVO dp = new DatiPartitaVO();	
						dp.setCodicePartita(idPartita);
						dp.setPronostico(torneo.getPronosticoUfficiale());
						return dp;
					}); 

					dpVO.setGoalCasa(risultatoPartita.getGoalCasa());

					dpVO.setGoalTrasferta(risultatoPartita.getGoalTrasferta());

					this.torneoBD.save(dpVO);

					PartitaVO partita = TorneoUtils.findPartita(idPartita, torneo);

					Partita rsModel = PartitaConverter.toRsModel(partita,dpVO, formatter);

					return ResponseEntity.ok(rsModel);
				});
			} catch(RuntimeException e) {
				this.logger.error("Invocazione terminata con errore '4xx': " +e.getMessage(),e);
				throw e;
			}
			catch(Throwable e) {
				this.logger.error("Invocazione terminata con errore: " +e.getMessage(),e);
				throw new InternalException(e);
			}
		});
	}

	@Override
	public ResponseEntity<Pronostico> postPronostico(final String idTorneo, final String idGiocatore, Resource body) {
		return this.torneoBD.runTransaction(() -> {
			try {
				this.torneoAuthorizationManager.autorizza(this.logger, this.request);


				TorneoVO torneo = this.torneoBD.findByName(idTorneo);

				GiocatoreVO giocatore = this.torneoBD.getGiocatore(idGiocatore);
				PronosticoVO p = PronosticoConverter.toPronosticoVO(torneo, giocatore, body, this.torneoBD);

				for(DatiPartitaVO dp: p.getDatiPartite()) {
					this.torneoBD.save(dp);
				}
				this.torneoBD.create(p);
				Pronostico rsModel = PronosticoConverter.toRsModel(p, ClassificaGiocone.getPuntiPronostico(p, torneo.getPronosticoUfficiale()));

				return ResponseEntity.ok(rsModel);
			} catch(RuntimeException e) {
				this.logger.error("Invocazione terminata con errore '4xx': " +e.getMessage(),e);
				throw e;
			}
			catch(Throwable e) {
				this.logger.error("Invocazione terminata con errore: " +e.getMessage(),e);
				throw new InternalException(e);
			}
		});
	}

	@Override
	public ResponseEntity<Resource> getPronosticoRaw(String idTorneo, String idGiocatore) {
		return this.torneoBD.runTransaction(() -> {
			try {
				TorneoVO torneo = this.torneoBD.findByName(idTorneo);

				PronosticoVO p = torneo.getPronostici().stream().filter(pr -> {
					return pr.getGiocatore().getNome().equals(idGiocatore);
				}).findFirst().orElseThrow(() -> new NotFoundException("Pronostico per giocatore ["+idGiocatore+"] e torneo ["+idTorneo+"] non trovato"));
				

				return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=tabellone-euro2020_"+idGiocatore+".xlsx").body(new ByteArrayResource(p.getPronosticoOriginale()));
			} catch(RuntimeException e) {
				this.logger.error("Invocazione terminata con errore '4xx': " +e.getMessage(),e);
				throw e;
			}
			catch(Throwable e) {
				this.logger.error("Invocazione terminata con errore: " +e.getMessage(),e);
				throw new InternalException(e);
			}
		});
	}
}
