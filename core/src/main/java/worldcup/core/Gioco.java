package worldcup.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Gioco {

	private Map<String, Pronostico> pronostici;
	private Pronostico ufficiale;

	public Gioco() {
		this.pronostici = PronosticiReader.leggiPronostici();
		Player playerUfficiale = new Player();
		playerUfficiale.setId(WorldCupProperties.getInstance().getIdPronosticoUfficiale());
		this.ufficiale= new Pronostico(playerUfficiale);
	}

	public List<Match> matchPerData(Date dataInizio, Date dataFine) {
		return ufficiale.getTorneo().getMatches().values().stream().filter(a-> 
			a.getDataMatch().before(dataFine) && a.getDataMatch().after(dataInizio)
		).collect(Collectors.toList());
	}

	public Match getMatch(String idMatch) {
		return ufficiale.getTorneo().getMatches().get(idMatch);
	}

	public Map<Player, Match> pronosticiPerMatch(Match match) {
		
		Map<Player, Match> map = new HashMap<>();
		
		for(String k: pronostici.keySet()) {
			for(Match matchCorrispondente: pronostici.get(k).getTorneo().getMatches().values()) {
				if(matchCorrispondente.equi(match)) {
					map.put(pronostici.get(k).getPlayer(), matchCorrispondente);
				}
			}
			
		}
		
		return map;
	}

	public Collection<Match> getMatchList() {
		return this.ufficiale.getTorneo().getMatches().values();
	}

	public void setResult(Match match, int goalHome, int goalAway) {
		this.ufficiale.inserisciPronostico(match.getMatchId(), goalHome, goalAway);
	}
	
	public Map<Pronostico, Integer> getClassifica() {
		Map<Pronostico, Integer> classifica = new HashMap<Pronostico, Integer>();

		for(String k: pronostici.keySet()) {
			Pronostico pronostico = pronostici.get(k);
			classifica.put(pronostico, ufficiale.getTorneo().getPoints(pronostico.getTorneo()));
		}

		LinkedHashMap<Pronostico, Integer> reverseSortedMap = new LinkedHashMap<>();
		
		//Use Comparator.reverseOrder() for reverse ordering
		classifica.entrySet()
		    .stream()
		    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
		    .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
		
		return reverseSortedMap;
	}
	
	public List<Pronostico> getListaPronostici(){
		List<String> listaKey = new ArrayList<>();
		listaKey.addAll(this.pronostici.keySet());
		Collections.sort(listaKey);
		
		List<Pronostico> lstRet = new ArrayList<>();
		for (String key : listaKey) {
			lstRet.add(this.pronostici.get(key));
		}
		
		return lstRet;
	}
}
