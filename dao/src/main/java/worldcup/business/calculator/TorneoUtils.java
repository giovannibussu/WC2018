package worldcup.business.calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import worldcup.orm.vo.DatiPartitaVO;
import worldcup.orm.vo.PartitaVO;
import worldcup.orm.vo.PronosticoVO;
import worldcup.orm.vo.SquadraVO;
import worldcup.orm.vo.SubdivisionVO;
import worldcup.orm.vo.SubdivisionVO.TIPO;
import worldcup.orm.vo.TorneoVO;

public class TorneoUtils {

	public enum CasaTrasfertaEnum {CASA, TRASFERTA}

	public static PartitaVO findPartita(String idPartita, TorneoVO torneo) {
		for(SubdivisionVO s: torneo.getSubdivisions()) {
			for(PartitaVO p: s.getPartite()) {
				if(p.getCodicePartita().equals(idPartita)) {
					return p;
				}
			}
		}

		throw new RuntimeException("Partita ["+idPartita+"] non trovata nel torneo ["+torneo.getNome()+"]");
	}

	public static SquadraVO getSquadra(PartitaVO partita, DatiPartitaVO dati, CasaTrasfertaEnum casaTrasferta) {
		if(!isGiocabile(dati)) return null;
		if(isCasa(casaTrasferta)) {
			return partita.getCasa();
		} else {
			return partita	.getTrasferta();
		}
	}

	public static SquadraVO getSquadraContro(PartitaVO partita, DatiPartitaVO dati, CasaTrasfertaEnum casaTrasferta) {
		if(!isGiocabile(dati)) return null;
		if(isCasa(casaTrasferta)) {
			return partita.getTrasferta();
		} else {
			return partita.getCasa();
		}
	}

	public static DatiPartitaVO getDatiPartita(String idPartita, PronosticoVO p) {
		return getOptDatiPartita(idPartita, p).orElseThrow(() -> new RuntimeException("Partita non trovata"));
	}

	public static Optional<DatiPartitaVO> getOptDatiPartita(String idPartita, PronosticoVO p) {
		return p.getDatiPartite().stream().filter(pa -> pa.getCodicePartita().equals(idPartita)).findAny();
	}

	public static WrapperDatiPartita getDatiPartitaEqui(PartitaVO partita, TorneoVO torneoPronosticato) {
		
		SubdivisionVO s = torneoPronosticato.getSubdivisions().stream().filter(su -> partita.getSubdivision().getNome().equals(su.getNome()))
				.findAny().orElseThrow(() -> new RuntimeException("Subdivision ["+partita.getSubdivision().getNome()+"] non trovata nel pronostico ["+torneoPronosticato.getPronosticoUfficiale().getGiocatore()+"]"));

		for(PartitaVO p: s.getPartite()) {
			boolean reverse = isReverse(p, partita);
			if(isEqui(p, partita) || reverse) {
//				System.out.println("TROVATA " + p.getCasa().getNome() + " " + p.getTrasferta().getNome());
				WrapperDatiPartita w = new WrapperDatiPartita();
				w.setDatiPartita(getOptDatiPartita(p.getCodicePartita(), torneoPronosticato.getPronosticoUfficiale()));
				w.setReverse(reverse);
				return w;
			}
		}
		
		return new WrapperDatiPartita();
	}

	private static boolean isEqui(PartitaVO p, PartitaVO partita) {
		return isStessaSquadra(p.getCasa(), partita.getCasa()) &&
				isStessaSquadra(p.getTrasferta(), partita.getTrasferta());
	}



	private static boolean isStessaSquadra(SquadraVO casa, SquadraVO trasferta) {
		return casa.getNome().equals(trasferta.getNome());
	}



	private static boolean isReverse(PartitaVO p, PartitaVO partita) {
		return isStessaSquadra(p.getTrasferta(), partita.getCasa()) &&
				isStessaSquadra(p.getCasa(), partita.getTrasferta());
	}



	public static boolean isCasa(CasaTrasfertaEnum casaTrasferta) {
		return casaTrasferta.equals(CasaTrasfertaEnum.CASA);
	}

	public static boolean isGiocata(DatiPartitaVO dati) {
		return isGiocabile(dati) &&
				dati.getGoalCasa()!= null && 
						dati.getGoalTrasferta()!= null;
	}

	public static boolean isGiocabile(DatiPartitaVO dati) {
		return dati!= null;
	}
	
	public static Integer getGoalFatti(DatiPartitaVO dati, CasaTrasfertaEnum casaTrasferta) {
		if(!isGiocata(dati)) return null;
		if(isCasa(casaTrasferta)) {
			return dati.getGoalCasa();
		} else {
			return dati.getGoalTrasferta();
		}
	}


	public static Integer getGoalSubiti(DatiPartitaVO dati, CasaTrasfertaEnum casaTrasferta) {
		if(!isGiocata(dati)) return null;
		if(isCasa(casaTrasferta)) {
			return dati.getGoalTrasferta();
		} else {
			return dati.getGoalCasa();
		}
	}

	public static Integer getPunti(DatiPartitaVO dati, CasaTrasfertaEnum casaTrasferta) {
		if(!isGiocata(dati)) return null;
		if(isCasa(casaTrasferta)) {
			return getPunti(dati.getGoalCasa(),dati.getGoalTrasferta());
		} else {
			return getPunti(dati.getGoalTrasferta(),dati.getGoalCasa());
		}
	}

	public static Integer getVittoria(DatiPartitaVO dati, CasaTrasfertaEnum casaTrasferta) {
		if(!isGiocata(dati)) return null;
		if(isCasa(casaTrasferta)) {
			return getVittoria(dati.getGoalCasa(),dati.getGoalTrasferta());
		} else {
			return getVittoria(dati.getGoalTrasferta(),dati.getGoalCasa());
		}
	}

	public static String getRisultatoEsatto(DatiPartitaVO dati, boolean reverse) {
		if(reverse) {
			return dati.getGoalTrasferta() + "-" + dati.getGoalCasa();
		} else {
			return dati.getGoalCasa() + "-" + dati.getGoalTrasferta();
		}
	}

	public static String getRisultato1x2(DatiPartitaVO dati, boolean reverse) {

		if(dati.getGoalCasa() > dati.getGoalTrasferta()) {
			return reverse ? "2" : "1";
		} else if(dati.getGoalTrasferta() > dati.getGoalCasa()) {
			return reverse ? "1" : "2";
		} else {
			return "X";
		}
	}

	private static int getPunti(int goal1, int goal2) {
		if(goal1 == goal2) return 1;
		if(goal1 > goal2) return 3;
		return 0;
	}

	private static int getVittoria(int goal1, int goal2) {
		if(goal1 > goal2) return 1;
		return 0;
	}

	public static TorneoVO getTorneoPronosticato(PronosticoVO pronostico) {
		TorneoVO torneo = new TorneoVO();
		torneo.setNome(pronostico.getTorneo().getNome());
		torneo.setPronosticoUfficiale(pronostico);


		
		Collection<SubdivisionVO> gironi = getSubdivisions(pronostico.getTorneo(), TIPO.GIRONE);
		torneo.getSubdivisions().addAll(gironi);
		
		if(!isDone(pronostico.getTorneo(), TIPO.GIRONE, pronostico)) {
			return torneo;
		}
		
		GironeResult result = getGironeResult(pronostico, gironi);
		
//		System.out.println(result);
		ComposizioneOttavi ott = new ComposizioneOttavi();
		ComposizioneKnockout cko = new ComposizioneKnockout();

		KnockoutCalculator kcal = new KnockoutCalculator();

		SubdivisionVO ottavi = getSubdivision(pronostico.getTorneo(), TIPO.OTTAVI);

		SubdivisionVO ottaviS = ott.getPartite(result, null, ottavi);
		torneo.getSubdivisions().add(ottaviS);

		if(!isDone(pronostico.getTorneo(), TIPO.OTTAVI, pronostico)) {
			return torneo;
		}
		
		KnockoutResult ottaviResult = kcal.getResult(ottaviS, pronostico);

		SubdivisionVO quarti = getSubdivision(pronostico.getTorneo(), TIPO.QUARTI);
		SubdivisionVO quartiS = cko.getPartite(ottaviResult, quarti);
		
		torneo.getSubdivisions().add(quartiS);

		if(!isDone(pronostico.getTorneo(), TIPO.QUARTI, pronostico)) {
			return torneo;
		}

		KnockoutResult quartiResult = kcal.getResult(quartiS, pronostico);

		SubdivisionVO semifinali = getSubdivision(pronostico.getTorneo(), TIPO.SEMIFINALE);
		SubdivisionVO semifinaliS = cko.getPartite(quartiResult, semifinali);

		torneo.getSubdivisions().add(semifinaliS);

		if(!isDone(pronostico.getTorneo(), TIPO.SEMIFINALE, pronostico)) {
			return torneo;
		}
		
		KnockoutResult semifinaliResult = kcal.getResult(semifinaliS, pronostico);
		
		SubdivisionVO finale = getSubdivision(pronostico.getTorneo(), TIPO.FINALE);
		SubdivisionVO finaleS = cko.getPartite(semifinaliResult, finale);
		
		torneo.getSubdivisions().add(finaleS);

		if(!isDone(pronostico.getTorneo(), TIPO.FINALE, pronostico)) {
			return torneo;
		}
		
		KnockoutResult finaleResult = kcal.getResult(finaleS, pronostico);

		SquadraVO winner = finaleResult.getWinners().get(finale.getPartite().stream().findAny().orElseThrow().getCodicePartita());
		
                if(pronostico.getVincente() != null && !pronostico.getVincente().getNome().equals(winner.getNome())) {
//			throw new RuntimeException("Giocatore ["+pronostico.getGiocatore().getNome()+"] Pronosticato vincente ["+pronostico.getVincente().getNome()+"] calcolato ["+winner.getNome()+"]");
			System.err.println("Giocatore ["+pronostico.getGiocatore().getNome()+"] Pronosticato vincente ["+pronostico.getVincente().getNome()+"] calcolato ["+winner.getNome()+"]");
		}

		
		return torneo;
	}

	public static GironeResult getGironeResult(PronosticoVO pronostico, Collection<SubdivisionVO> gironi) {
		GironeCalculator cal = new GironeCalculator();
		
		RegoleGirone conf = new RegoleGirone();
		
		Regole regoleClassificaAvulsa = new Regole();
		List<IPerformanceEvaluator<GironePerformance>> regoleClassificaAvulsaLst = new ArrayList<IPerformanceEvaluator<GironePerformance>>();
		
		regoleClassificaAvulsaLst.add(new PuntiGironePerformanceEvaluator());
		regoleClassificaAvulsaLst.add(new GoalDifferenceGironePerformanceEvaluator());
		regoleClassificaAvulsaLst.add(new GoalsDoneGironePerformanceEvaluator());
		regoleClassificaAvulsaLst.add(new NumeroVittorieGironePerformanceEvaluator());
		
		regoleClassificaAvulsa.setRegole(regoleClassificaAvulsaLst);
		

		Regole regoleVerticali = new Regole();
		List<IPerformanceEvaluator<GironePerformance>> regoleVerticaliLst = new ArrayList<IPerformanceEvaluator<GironePerformance>>();
		
		regoleVerticaliLst.add(new PuntiGironePerformanceEvaluator());
		regoleVerticaliLst.add(new ClassificaAvulsaPerformanceEvaluator(regoleClassificaAvulsa));
		regoleVerticaliLst.add(new GoalDifferenceGironePerformanceEvaluator());
		regoleVerticaliLst.add(new GoalsDoneGironePerformanceEvaluator());
		regoleVerticaliLst.add(new NumeroVittorieGironePerformanceEvaluator());
		regoleVerticaliLst.add(new RankingPerformanceEvaluator());
//		regoleVerticaliLst.add(new AlfabeticoPerformanceEvaluator());
		regoleVerticali.setRegole(regoleVerticaliLst);
		conf.setRegoleVerticali(regoleVerticali );


		Regole regoleOrizzontali = new Regole();
		List<IPerformanceEvaluator<GironePerformance>> regoleOrizzontaliLst = new ArrayList<IPerformanceEvaluator<GironePerformance>>();
		
		regoleOrizzontaliLst.add(new PuntiGironePerformanceEvaluator());
		regoleOrizzontaliLst.add(new GoalDifferenceGironePerformanceEvaluator());
		regoleOrizzontaliLst.add(new GoalsDoneGironePerformanceEvaluator());
		regoleOrizzontaliLst.add(new NumeroVittorieGironePerformanceEvaluator());
		regoleOrizzontaliLst.add(new RankingPerformanceEvaluator());
//		regoleOrizzontaliLst.add(new AlfabeticoPerformanceEvaluator());
		regoleOrizzontali.setRegole(regoleOrizzontaliLst);

		conf.setRegoleOrizzontali(regoleOrizzontali);
		GironeResult result = cal.getResult(gironi, pronostico, conf);
		return result;
	}
	
	public static SubdivisionVO getSubdivision(TorneoVO torneo, TIPO tipo) {
		return torneo.getSubdivisions().stream().filter(s -> s.getTipo().equals(tipo)).findAny().orElseThrow();
	}
	
	public static Collection<SubdivisionVO> getSubdivisions(TorneoVO torneo, TIPO tipo) {
		return torneo.getSubdivisions().stream().filter(s -> s.getTipo().equals(tipo)).collect(Collectors.toList());
	}
	
	public static boolean isDone(TorneoVO torneo, TIPO tipo, PronosticoVO pronostico) {
		switch(tipo) {
		case FINALE: 
		case OTTAVI:
		case QUARTI:
		case SEMIFINALE: return allPlayed(Arrays.asList(getSubdivision(torneo, tipo)), torneo, pronostico);
		case GIRONE: return allPlayed(getSubdivisions(torneo, tipo), torneo, pronostico);
		default:
			break;}
		
		return false;
	}

	public static boolean isGiocabile(TorneoVO torneo, TIPO tipo, PronosticoVO pronostico) {
		switch(tipo) {
		case FINALE: 
		case OTTAVI:
		case QUARTI:
		case SEMIFINALE: return allDefined(Arrays.asList(getSubdivision(torneo, tipo)), torneo, pronostico);
		case GIRONE: return allDefined(getSubdivisions(torneo, tipo), torneo, pronostico);
		default:
			break;}
		
		return false;
	}

	private static boolean allPlayed(Collection<SubdivisionVO> asList, TorneoVO torneo, PronosticoVO pronostico) {
		
		for(SubdivisionVO s: asList) {
//			System.out.println("SUBDIVISION " + s.getNome());
			for(PartitaVO p: s.getPartite()) {
//				System.out.println("PARTITA " + p.getCodicePartita());
				Optional<DatiPartitaVO> datiPartita = getOptDatiPartita(p.getCodicePartita(), pronostico);//torneo.getPronosticoUfficiale());
				if(!datiPartita.isPresent()) {
					return false;
				}
				if(!isGiocata(datiPartita.get())) {
					return false;
				}
			}
		}
		
		return true;
	}

	private static boolean allDefined(Collection<SubdivisionVO> asList, TorneoVO torneo, PronosticoVO pronostico) {
		
		for(SubdivisionVO s: asList) {
			for(PartitaVO p: s.getPartite()) {
				if(p.getCasa()==null) {
					return false;
				}
				if(p.getTrasferta()==null) {
					return false;
				}
			}
		}
		
		return true;
	}

	public static List<Distribuzione> getDistribuzione(TorneoVO torneo, String idPartita, boolean distr1x2) {
		List<Distribuzione> distr = new ArrayList<>();
		
		Map<String, List<String>> mappaNomi = new HashMap<>();
		for(PronosticoVO p: torneo.getPronostici()) {
			
			TorneoVO torneoPronosticato = getTorneoPronosticato(p);
			
			PartitaVO partitaUfficiale = findPartita(idPartita, torneo);
			WrapperDatiPartita w = getDatiPartitaEqui(partitaUfficiale, torneoPronosticato);
			
			if(w.getDatiPartita().isPresent()) {
				
				String idPartitaPronostico = w.getDatiPartita().get().getCodicePartita();
				String key = null;
				if(distr1x2) {
					key = getRisultato1x2(getDatiPartita(idPartitaPronostico, p), w.isReverse());
				} else {
					key = getRisultatoEsatto(getDatiPartita(idPartitaPronostico, p), w.isReverse());
				}
				if(!mappaNomi.containsKey(key)) {
					List<String> lst = new ArrayList<>();
					lst.add(p.getGiocatore().getNome());
					mappaNomi.put(key, lst);
				} else {
					List<String> lst = mappaNomi.get(key);
					lst.add(p.getGiocatore().getNome());
					mappaNomi.put(key, lst);
				}
			}
		}
		
		for(String p: mappaNomi.keySet()) {
			Distribuzione distribuzione = new Distribuzione();
			distribuzione.setLabel(p);
			List<String> value = mappaNomi.get(p);
			distribuzione.setValue(value.size());
			distribuzione.setTooltip(String.join("<br/>", value));
			distr.add(distribuzione);
		}

		return distr;
	}

	public static boolean isGiocabile(TorneoVO torneo, String codicePartita) {
		
		return findPartita(codicePartita, torneo).getCasa() != null; //TODO
	}

	public static boolean daGiocare(TorneoVO torneo, String codicePartita) {
		return !torneo.getPronosticoUfficiale().getDatiPartite().stream().anyMatch(dp -> dp.getCodicePartita().equals(codicePartita));
	}

	public static boolean isRisultatoEsatto(PartitaVO partitaUfficiale, DatiPartitaVO datiPartitaUfficiale,
			PartitaVO partitaPronostico, DatiPartitaVO datiPartitaPronostico, boolean isReverse) {
		
		if(isEqui(partitaUfficiale, partitaPronostico) || isReverse) {
			return getRisultatoEsatto(datiPartitaUfficiale, false).equals(getRisultatoEsatto(datiPartitaPronostico, isReverse(partitaUfficiale, partitaPronostico)));
		} else {
			return false;
		}
	}
	
	public static boolean isRisultato1x2(PartitaVO partitaUfficiale, DatiPartitaVO datiPartitaUfficiale,
			PartitaVO partitaPronostico, DatiPartitaVO datiPartitaPronostico, boolean isReverse) {
		if(isEqui(partitaUfficiale, partitaPronostico) || isReverse) {
			return getRisultato1x2(datiPartitaUfficiale, false).equals(getRisultato1x2(datiPartitaPronostico, isReverse(partitaUfficiale, partitaPronostico)));
		} else {
			return false;
		}
	}

	public static Integer getGoalTrasferta(DatiPartitaVO dp, boolean isReverse) {
		return isReverse ? dp.getGoalCasa() : dp.getGoalTrasferta();
	}

	public static Integer getGoalCasa(DatiPartitaVO dp, boolean isReverse) {
		return isReverse ? dp.getGoalTrasferta() : dp.getGoalCasa();
	}
	
	
}
