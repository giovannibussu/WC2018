package worldcup.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
		
		Comparator<Match> byDate = 
				(Match o1, Match o2)->o1.getDataMatch().compareTo(o2.getDataMatch());
		
		return ufficiale.getTorneo().getMatches().values().stream().filter(a-> 
			a.getDataMatch().before(dataFine) && a.getDataMatch().after(dataInizio)
		).sorted(byDate).collect(Collectors.toList());
	}

	public Match getMatch(String idMatch) {
		Match match = ufficiale.getTorneo().getMatches().get(idMatch);
		return match;
	}
	
	public Grafico distribuzionePronosticiPerMatchRisultatoEsatto(Match match) {
		Grafico grafico = _distribuzionePronosticiPerMatchRisultatoEsatto(match);
		grafico.setTitolo("");
		grafico.setSottotitolo("");
		return grafico;
	}
	
	public Grafico distribuzionePronosticiPerMatchRisultato(Match match) {
		Grafico grafico = _distribuzionePronosticiPerMatchRisultato(match);
		grafico.setTitolo("");
		grafico.setSottotitolo("");
		return grafico;
	}

	public Grafico _distribuzionePronosticiPerMatchBase(Match match, Function<Match, String> groupBy) {
		Grafico grafico = new Grafico();
		Map<String, List<Match>> pronosticiPerMatch = pronosticiPerMatch(match).values().stream()
				.collect(Collectors.groupingBy(groupBy));

		List<Distribuzione> distr = new ArrayList<>();
		for(String p: pronosticiPerMatch.keySet()) {
			Distribuzione distribuzione = new Distribuzione();
			distribuzione.setLabel(p);
			distribuzione.setValue(pronosticiPerMatch.get(p).size());
			
			distribuzione.setTooltip(pronosticiPerMatch.get(p).size() +"");
			distr.add(distribuzione);
		}
		
		grafico.setDati(distr);
		
		return grafico;
	}


	public Grafico _distribuzionePronosticiPerMatchRisultatoEsatto(Match match) {
		Function<Match, String> risultatoEsatto = (Match m) -> m.getRisultatoEsatto(match);

		Grafico grafico = new Grafico();
		Map<Player, Match> pronosticiPerMatchMap = pronosticiPerMatch(match);

		Map<String, String> mappaNomi = new HashMap<>();
		
		for(Player player: pronosticiPerMatchMap.keySet()) {
			String key =pronosticiPerMatchMap.get(player).getRisultatoEsatto(match);
			if(!mappaNomi.containsKey(key)) {
				mappaNomi.put(key, player.getNome());
			} else {
				String string = mappaNomi.get(key);
				mappaNomi.put(key, string + "<br/>" +player.getNome());
				
			}
		}

		Map<String, List<Match>> pronosticiPerMatch = pronosticiPerMatchMap.values().stream()
				.collect(Collectors.groupingBy(risultatoEsatto));

		List<Distribuzione> distr = new ArrayList<>();
		for(String p: mappaNomi.keySet()) {
			Distribuzione distribuzione = new Distribuzione();
			distribuzione.setLabel(p);
			distribuzione.setValue(pronosticiPerMatch.get(p).size());
			distribuzione.setTooltip(mappaNomi.get(p));
			distr.add(distribuzione);
		}
		
		grafico.setDati(distr);
		
		return grafico;
	}

	public Grafico _distribuzionePronosticiPerMatchRisultato(Match match) {
		Function<Match, String> risultatoEsatto = (Match m) -> m.getRisultato(match).name();
		Grafico grafico = new Grafico();
		Map<String, List<Match>> pronosticiPerMatch = pronosticiPerMatch(match).values().stream()
				.collect(Collectors.groupingBy(risultatoEsatto));

		List<Distribuzione> distr = new ArrayList<>();
		for(String p: pronosticiPerMatch.keySet()) {
			Distribuzione distribuzione = new Distribuzione();
			distribuzione.setLabel(getRisultatoLabel(p));
			distribuzione.setValue(pronosticiPerMatch.get(p).size());
			distribuzione.setTooltip(pronosticiPerMatch.get(p).size() +"");
			distr.add(distribuzione);
		}
		
		grafico.setDati(distr);
		
		return grafico;
	}
	
	public String getRisultatoLabel (String s) {
		if(s.equals("_1")) {
			return "1";
		} else if(s.equals("_2")) {
			return "2";
		} else {
			return "X";
		}
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
		
		LinkedHashMap<Player, Match> sortedMap = new LinkedHashMap<>();
		
		map.entrySet()
	    .stream()
	    .sorted(Map.Entry.comparingByKey())
	    .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
		
		return sortedMap;
	}

	public List<Match> getMatchList() {
		List<Match> listaMatch = new ArrayList<Match>();
		listaMatch.addAll(this.ufficiale.getTorneo().getMatches().values().stream().filter(match -> match.isPlayable() && !match.isPlayed())
			    .sorted()
				.collect(Collectors.toList()));
		return listaMatch;
	}

	public void setResult(Match match, int goalHome, int goalAway) {
		System.out.println("Match ["+match.getMatchId()+"], Gol Home ["+goalHome+"], Gol Away ["+goalAway+"]"); 
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
	
	public boolean check(String username, String password) {
		return WorldCupProperties.getInstance().getUsername().equals(username) &&
				WorldCupProperties.getInstance().getPassword().equals(password);
	}
}
