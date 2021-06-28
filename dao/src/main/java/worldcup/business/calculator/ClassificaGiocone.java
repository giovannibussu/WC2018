package worldcup.business.calculator;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import worldcup.orm.vo.DatiPartitaVO;
import worldcup.orm.vo.PartitaVO;
import worldcup.orm.vo.PronosticoVO;
import worldcup.orm.vo.SubdivisionVO;
import worldcup.orm.vo.SubdivisionVO.TIPO;
import worldcup.orm.vo.TorneoVO;

public class ClassificaGiocone {

	public static Map<PronosticoVO, Integer> getClassifica(TorneoVO torneo) {
		
		Map<PronosticoVO, Integer> map = new HashMap<>();
		for(PronosticoVO pronostico: torneo.getPronostici()) {

			if(!pronostico.getId().equals(torneo.getPronosticoUfficiale().getId())) {
				int punti = getPuntiPronostico(pronostico);
				
				map.put(pronostico, punti);
			}
		}
		
		return map;
	}

	public static Integer getPuntiPronostico(RisultatoPronostico risultatoPronostico) {

		int punti = 0;
		
		for(Entry<SubdivisionVO, Integer> r: risultatoPronostico.getRisultati().entrySet()) {
			punti += r.getKey().getPunteggi().getPuntiPerRisultato() * r.getValue();
		}
		for(Entry<SubdivisionVO, Integer> r: risultatoPronostico.getRisultatiEsatti().entrySet()) {
			punti += r.getKey().getPunteggi().getPuntiPerRisultatoEsatto() * r.getValue();
		}
		for(Entry<SubdivisionVO, Integer> r: risultatoPronostico.getPassaggi().entrySet()) {
			punti += r.getKey().getPunteggi().getPuntiPerPassaggi() * r.getValue();
		}
		for(Entry<SubdivisionVO, Integer> r: risultatoPronostico.getPosizioni().entrySet()) {
			punti += r.getKey().getPunteggi().getPuntiPerPosizione() * r.getValue();
		}
		
		return punti;

	}

	public static Integer getPuntiPronostico(PronosticoVO pronostico) {
		
			RisultatoPronostico risultatoPronostico = getRisultatoPronostico(pronostico.getTorneo(), TorneoUtils.getTorneoPronosticato(pronostico));
			return getPuntiPronostico(risultatoPronostico);
	}

	private static RisultatoPronostico getRisultatoPronostico(TorneoVO torneoUfficiale, TorneoVO torneoPronostico) {

		RisultatoPronostico rp = new RisultatoPronostico();
		for(DatiPartitaVO datiPartitaUfficiale: torneoUfficiale.getPronosticoUfficiale().getDatiPartite()) {
			PartitaVO partitaUfficiale = TorneoUtils.findPartita(datiPartitaUfficiale.getCodicePartita(), torneoUfficiale);
			WrapperDatiPartita w = TorneoUtils.getDatiPartitaEqui(partitaUfficiale, torneoPronostico);
			if(w.getDatiPartita().isPresent()) {
				DatiPartitaVO datiPartitaPronostico = w.getDatiPartita().get();
				PartitaVO partitaPronostico = TorneoUtils.findPartita(datiPartitaPronostico.getCodicePartita(), torneoPronostico);
				if(TorneoUtils.isRisultatoEsatto(partitaUfficiale, datiPartitaUfficiale, partitaPronostico, datiPartitaPronostico, w.isReverse())) {
					rp.addRisultatiEsatti(partitaUfficiale.getSubdivision());
				} else if(TorneoUtils.isRisultato1x2(partitaUfficiale, datiPartitaUfficiale, partitaPronostico, datiPartitaPronostico, w.isReverse())) {
					rp.addRisultati(partitaUfficiale.getSubdivision());
				}
			}
		}

		for(TIPO tipo: TIPO.values()) {
			if(TorneoUtils.isGiocabile(torneoUfficiale, tipo, torneoUfficiale.getPronosticoUfficiale())) {
				Set<String> squadreUff = new HashSet<>();
				Set<String> squadrePron = new HashSet<>();
				
				SubdivisionVO subdivisionUff = TorneoUtils.getSubdivision(torneoUfficiale, tipo);
				SubdivisionVO subdivisionPron = TorneoUtils.getSubdivision(torneoPronostico, tipo);
				
				squadreUff.addAll(subdivisionUff.getPartite().stream().map(p -> p.getCasa().getNome()).collect(Collectors.toSet()));
				squadreUff.addAll(subdivisionUff.getPartite().stream().map(p -> p.getTrasferta().getNome()).collect(Collectors.toSet()));
	
				squadrePron.addAll(subdivisionPron.getPartite().stream().map(p -> p.getCasa().getNome()).collect(Collectors.toSet()));
				squadrePron.addAll(subdivisionPron.getPartite().stream().map(p -> p.getTrasferta().getNome()).collect(Collectors.toSet()));
				
				for(String squadraUff: squadreUff) {
					if(squadrePron.contains(squadraUff)) {
						rp.addPassaggi(subdivisionUff);
					}
				}
			}
		}
		
		Map<TIPO, TIPO> successivo = new HashMap<>();
		
		successivo.put(TIPO.GIRONE, TIPO.OTTAVI);
		successivo.put(TIPO.OTTAVI, TIPO.QUARTI);
		successivo.put(TIPO.QUARTI, TIPO.SEMIFINALE);
		successivo.put(TIPO.SEMIFINALE, TIPO.FINALE);
		
		
		for(Entry<TIPO,TIPO> e: successivo.entrySet()) {
			if(TorneoUtils.isDone(torneoUfficiale, e.getKey(), torneoUfficiale.getPronosticoUfficiale())) {
	
				SubdivisionVO subdivisionUff = TorneoUtils.getSubdivision(torneoUfficiale, e.getValue());
							
				for(PartitaVO p: subdivisionUff.getPartite()) {
					PartitaVO p2 = TorneoUtils.findPartita(p.getCodicePartita(), torneoPronostico);
					
					if(p.getCasa().getNome().equals(p2.getCasa().getNome())) {
						rp.addPosizioni(subdivisionUff);
					}
					if(p.getTrasferta().getNome().equals(p2.getTrasferta().getNome())) {
						rp.addPosizioni(subdivisionUff);
					}
				}
				
			}
		}

		if(TorneoUtils.isDone(torneoUfficiale, TIPO.GIRONE, torneoUfficiale.getPronosticoUfficiale())) {

//			System.out.println("Pronostico " + torneoPronostico.getPronosticoUfficiale().getGiocatore().getNome());
			Collection<SubdivisionVO> subdivisions = TorneoUtils.getSubdivisions(torneoUfficiale, TIPO.GIRONE);
			GironeResult result = TorneoUtils.getGironeResult(torneoUfficiale.getPronosticoUfficiale(), subdivisions);
			
			Collection<SubdivisionVO> subdivisionsPron = TorneoUtils.getSubdivisions(torneoPronostico, TIPO.GIRONE);
			GironeResult resultPron = TorneoUtils.getGironeResult(torneoPronostico.getPronosticoUfficiale(), subdivisionsPron);
			
			for(SubdivisionVO s: subdivisions) {
				Classifica c = result.getClassificaVerticale(s.getNome());
				Classifica cPron = resultPron.getClassificaVerticale(s.getNome());
				
				for(Entry<Integer, GironePerformance> e : c.getSquadre().entrySet()) {
					GironePerformance perfProm = cPron.getSquadre().get(e.getKey());
					
					if(perfProm.getSquadra().getNome().equals(e.getValue().getSquadra().getNome())) {
//						System.out.println("SI " + s.getNome() + " " + e.getValue().getSquadra().getNome() + " " + e.getKey());
						rp.addPosizioni(s);
//					} else {
//						
//						System.out.println("NO " + s.getNome() + " " + e.getValue().getSquadra().getNome() + " " + e.getKey() + " aveva messo " + perfProm.getSquadra().getNome());
					}
				}
				
			}

	}

		return rp;
	}
}
