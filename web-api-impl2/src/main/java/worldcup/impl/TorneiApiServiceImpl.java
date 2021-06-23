package worldcup.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.format.Formatter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import worldcup.BadRequestException;
import worldcup.InternalException;
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
import worldcup.model.OrderType;
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

					lst.add(PronosticoConverter.toRsModel(k.getKey(), k.getValue(), formatter, false));

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
							Optional<DatiPartitaVO> dp = TorneoUtils.getOptDatiPartita(idPartita, torneo.getPronosticoUfficiale());
							return ResponseEntity.ok(PartitaConverter.toRsModel(p, dp, formatter));
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
					lst.add(PronosticoConverter.toRsModel(p, ClassificaGiocone.getPuntiPronostico(p), formatter, true));
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

				PartitaVO partita = TorneoUtils.findPartita(idPartita, torneo);
				System.out.println("Partita " + partita.getCodicePartita() + " casa " + partita.getCasa().getNome() + " trasferta " + partita.getTrasferta().getNome());

				List<PronosticoPartita> lst = new ArrayList<>();
				for(PronosticoVO p : torneo.getPronostici()) {
					System.out.println("Pronostico " + p.getGiocatore().getNome());
					
					TorneoVO torneoPronosticato = TorneoUtils.getTorneoPronosticato(p);
					Optional<DatiPartitaVO> dp = TorneoUtils.getDatiPartitaEqui(partita, torneoPronosticato);
					if(dp.isPresent()) {
						lst.add(PronosticoPartitaConverter.toRsModel(partita, dp.get(), p.getGiocatore(), formatter));
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

	public ResponseEntity<List<Partita>> listPartite(final String idTorneo, Integer limit, Long offset, String dataDa, String dataA, Boolean daGiocare, OrderType orderType) {
		return this.torneoBD.runTransaction(() -> {
			try {

				final Date datada;
				final Date dataa;
				if(dataDa == null) {
					Calendar nowCal = new GregorianCalendar();
					nowCal.set(Calendar.HOUR_OF_DAY, 0);
					nowCal.set(Calendar.MINUTE, 0);
					datada = new DateTime(nowCal.getTime()).toDate();
				} else {
					datada = formatter.parse(dataDa, Locale.getDefault()).toDate();
				}

				if(dataA == null) {
					Calendar nowCal = new GregorianCalendar();
					nowCal.set(Calendar.HOUR_OF_DAY, 0);
					nowCal.set(Calendar.MINUTE, 0);
					nowCal.add(Calendar.DATE, 6);
					Date tomorrow= nowCal.getTime();
					dataa = new DateTime(tomorrow.getTime()).toDate();
				} else {
					dataa = formatter.parse(dataA, Locale.getDefault()).toDate();
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
						if(orderType == null || orderType.equals(OrderType.ASC)) {
							return o1.getData().compareTo(o2.getData());
						} else {
							return o2.getData().compareTo(o1.getData());
						}
					}}).collect(Collectors.toList());


				List<Partita> lst = new ArrayList<>();

				int rOffset = offset != null ? offset.intValue() : 0;
				int rLimit = limit != null ? limit: 50;
				if(matchPerData != null) {
					for(int i = rOffset; i < rLimit && i < matchPerData.size(); i++){
						PartitaVO partitaVO = matchPerData.get(i);
						Optional<DatiPartitaVO> dp = torneo.getPronosticoUfficiale().getDatiPartite().stream()
								.filter(p -> p.getCodicePartita().equals(partitaVO.getCodicePartita()) &&
										!p.getId().equals(torneo.getPronosticoUfficiale().getId()))
								.findAny();
						if(TorneoUtils.isGiocabile(torneo, partitaVO.getCodicePartita())) {
							lst.add(PartitaConverter.toRsModel(partitaVO, dp, formatter));
						}
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
		});
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
					TorneoVO torneoPronosticato = TorneoUtils.getTorneoPronosticato(torneo.getPronosticoUfficiale());
					
					for(SubdivisionVO s: torneoPronosticato.getSubdivisions()) {
						for(PartitaVO p: s.getPartite()) {
							PartitaVO partita = TorneoUtils.findPartita(p.getCodicePartita(), torneo);

							if(p.getCasa() != null) {
								if(partita.getCasa() == null) {
									partita.setCasa(p.getCasa());
								}
							}
							if(p.getTrasferta() != null) {
								if(partita.getTrasferta() == null) {
									partita.setTrasferta(p.getTrasferta());
								}
							}

							this.torneoBD.create(partita);
						}
						
					}
					
					PartitaVO partita = TorneoUtils.findPartita(idPartita, torneo);

					Partita rsModel = PartitaConverter.toRsModel(partita,Optional.of(dpVO), formatter);

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

	public ResponseEntity<Partita> deleteRisultatoPartita(final String idTorneo, final String idPartita) {
		return this.torneoBD.runTransaction(() -> {
			try {
				this.torneoAuthorizationManager.autorizza(this.logger, this.request);

				return this.torneoBD.runTransaction(() -> {
					TorneoVO torneo = this.torneoBD.findByName(idTorneo);

					Optional<DatiPartitaVO> dpVO = TorneoUtils.getOptDatiPartita(idPartita, torneo.getPronosticoUfficiale()); 

					if(dpVO.isPresent()) {
						this.torneoBD.delete(dpVO.get());
					}

					PartitaVO partita = TorneoUtils.findPartita(idPartita, torneo);

					Partita rsModel = PartitaConverter.toRsModel(partita,Optional.empty(), formatter);

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
	public ResponseEntity<Pronostico> postPronostico(final String idTorneo, final String idGiocatore, String link, Resource body) {
		return this.torneoBD.runTransaction(() -> {
			try {
				this.torneoAuthorizationManager.autorizza(this.logger, this.request);


				TorneoVO torneo = this.torneoBD.findByName(idTorneo);

				GiocatoreVO giocatore = this.torneoBD.getGiocatore(idGiocatore);
				PronosticoVO p = PronosticoConverter.toPronosticoVO(torneo, giocatore, body, link, this.torneoBD);

				for(DatiPartitaVO dp: p.getDatiPartite()) {
					this.torneoBD.save(dp);
				}
				this.torneoBD.create(p);
				Pronostico rsModel = PronosticoConverter.toRsModel(p, ClassificaGiocone.getPuntiPronostico(p), formatter, true);

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
	public ResponseEntity<Pronostico> getPronostico(String idTorneo, String idGiocatore) {
		return this.torneoBD.runTransaction(() -> {
			try {

				TorneoVO torneo = this.torneoBD.findByName(idTorneo);

				PronosticoVO p = torneo.getPronostici().stream().filter(pr -> {return pr.getGiocatore().getNome().equals(idGiocatore);}).findAny()
						.orElseThrow(() -> new BadRequestException("Richiesta non valida"));

				Pronostico rsModel = PronosticoConverter.toRsModel(p, ClassificaGiocone.getPuntiPronostico(p), formatter, true);

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
	public ResponseEntity<Pronostico> getPronosticoUfficiale(String idTorneo) {
		return this.torneoBD.runTransaction(() -> {
			try {

				TorneoVO torneo = this.torneoBD.findByName(idTorneo);

				PronosticoVO p = torneo.getPronosticoUfficiale();

				p.setTorneo(torneo);
				
				Pronostico rsModel = PronosticoConverter.toRsModel(p, ClassificaGiocone.getPuntiPronostico(p), formatter, true);

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
}
