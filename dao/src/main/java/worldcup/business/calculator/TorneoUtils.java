package worldcup.business.calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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

	public static DatiPartitaVO getDatiPartita(String idPartita, PronosticoVO p) {
		return getOptDatiPartita(idPartita, p).orElseThrow(() -> new RuntimeException("Partita non trovata"));
	}

	public static Optional<DatiPartitaVO> getOptDatiPartita(String idPartita, PronosticoVO p) {
		return p.getDatiPartite().stream().filter(pa -> pa.getCodicePartita().equals(idPartita)).findAny();
	}

	public static Optional<DatiPartitaVO> getDatiPartitaEqui(PartitaVO partita, TorneoVO torneoPronosticato) {
		
		SubdivisionVO s = torneoPronosticato.getSubdivisions().stream().filter(su -> partita.getSubdivision().getNome().equals(su.getNome()))
				.findAny().orElseThrow(() -> new RuntimeException("Subdivision ["+partita.getSubdivision().getNome()+"] non trovata nel pronostico ["+torneoPronosticato.getPronosticoUfficiale().getGiocatore()+"]"));

		for(PartitaVO p: s.getPartite()) {
			if(isEqui(p, partita) || isReverse(p, partita)) {
				return Optional.of(getDatiPartita(p.getCodicePartita(), torneoPronosticato.getPronosticoUfficiale()));
			}
		}
		
		return Optional.empty();
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
//		return isGiocabile(dati) &&
//				dati.getGoalCasa()!= null && 
//						dati.getGoalTrasferta()!= null;
		return true;
	}

	public static boolean isGiocabile(DatiPartitaVO dati) {
		return dati!= null;
	}
	
	public static Integer getGoalFatti(DatiPartitaVO dati, CasaTrasfertaEnum casaTrasferta) {
		if(!isGiocata(dati)) return null;
		if(isCasa(casaTrasferta)) {
			return dati.getGoalTrasferta();
		} else {
			return dati.getGoalCasa();
		}
	}


	public static Integer getGoalSubiti(DatiPartitaVO dati, CasaTrasfertaEnum casaTrasferta) {
		if(!isGiocata(dati)) return null;
		if(isCasa(casaTrasferta)) {
			return dati.getGoalCasa();
		} else {
			return dati.getGoalTrasferta();
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

	public static TorneoVO getTorneoPronosticato(PronosticoVO pronostico) {
		TorneoVO torneo = new TorneoVO();
		torneo.setNome(pronostico.getTorneo().getNome());
		torneo.setPronosticoUfficiale(pronostico);
		Set<SubdivisionVO> subs = new HashSet<>();
		for(SubdivisionVO s: pronostico.getTorneo().getSubdivisions()) {
			
			if(s.getTipo().equals(TIPO.GIRONE)) {
				subs.add(s);				
			} else {
				SubdivisionVO s2 = new SubdivisionVO();
				s2.setNome(s.getNome());
				s2.setTipo(s.getTipo());
				Set<PartitaVO> partite = new HashSet<PartitaVO>();
				for(PartitaVO p: s.getPartite()) {
					PartitaVO p2 = merge(p, pronostico);
					partite.add(p2);
				}
				s2.setPartite(partite);
				subs.add(s2);
			}
		}
		torneo.setSubdivisions(subs);
				
		return torneo; // TODO
	}
	
	private static PartitaVO merge(PartitaVO p, PronosticoVO pronostico) {
		// TODO Auto-generated method stub
		return null;
	}



	public static List<Distribuzione> getDistribuzione(TorneoVO torneo, String idPartita, boolean distr1x2) {
		List<Distribuzione> distr = new ArrayList<>();
		
		Map<String, List<String>> mappaNomi = new HashMap<>();
		for(PronosticoVO p: torneo.getPronostici()) {
			
			TorneoVO torneoPronosticato = getTorneoPronosticato(p);
			
			PartitaVO partitaUfficiale = findPartita(idPartita, torneo);
			Optional<DatiPartitaVO> optPartita = getDatiPartitaEqui(partitaUfficiale, torneoPronosticato);
			
			if(optPartita.isPresent()) {
				
				String idPartitaPronostico = optPartita.get().getCodicePartita();
				PartitaVO partitaPronostico = findPartita(idPartitaPronostico, torneo);
				boolean reverse = isReverse(partitaUfficiale, partitaPronostico);

				String key = null;
				if(distr1x2) {
					key = getRisultato1x2(getDatiPartita(idPartitaPronostico, p), reverse);
				} else {
					key = getRisultatoEsatto(getDatiPartita(idPartitaPronostico, p), reverse);
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

	public static boolean daGiocare(TorneoVO torneo, String codicePartita) {
		return !torneo.getPronosticoUfficiale().getDatiPartite().stream().anyMatch(dp -> dp.getCodicePartita().equals(codicePartita));
	}

	public static boolean isRisultatoEsatto(PartitaVO partitaUfficiale, DatiPartitaVO datiPartitaUfficiale,
			PartitaVO partitaPronostico, DatiPartitaVO datiPartitaPronostico) {
		if(isEqui(partitaUfficiale, partitaPronostico)) {
			return getRisultatoEsatto(datiPartitaUfficiale, true).equals(getRisultatoEsatto(datiPartitaPronostico, isReverse(partitaUfficiale, partitaPronostico)));
		} else {
			return false;
		}
	}
	
	public static boolean isRisultato1x2(PartitaVO partitaUfficiale, DatiPartitaVO datiPartitaUfficiale,
			PartitaVO partitaPronostico, DatiPartitaVO datiPartitaPronostico) {
		if(isEqui(partitaUfficiale, partitaPronostico)) {
			return getRisultato1x2(datiPartitaUfficiale, true).equals(getRisultato1x2(datiPartitaPronostico, isReverse(partitaUfficiale, partitaPronostico)));
		} else {
			return false;
		}
	}
	
	
}
