package worldcup.core;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Gioco {

	private Map<String, Pronostico> pronostici;
	private Pronostico ufficiale;
	
	public Gioco() {
		this.pronostici = new HashMap<>();
		this.ufficiale= new Pronostico();
	}
	
	public List<Match> matchPerData(Date dataInizio, Date dataFine) {
		return ufficiale.getTorneo().getMatches().values().stream().filter(a-> 
			a.getDataMatch().before(dataFine) && a.getDataMatch().after(dataInizio)
		).collect(Collectors.toList());
	}
	
	public List<Match> pronosticiPerMatch(Match match) {
		return pronostici.values().stream().map(a-> 
		a.getTorneo().getMatches().values().stream().filter(b-> b.equi(match)).collect(Collectors.toList()).get(0)
		).collect(Collectors.toList());
	}
	
	public Map<Pronostico, Integer> getClassifica() {
		Map<Pronostico, Integer> classifica = new HashMap<Pronostico, Integer>();
		
		for(String k: pronostici.keySet()) {
			Pronostico pronostico = pronostici.get(k);
			classifica.put(pronostico, ufficiale.getTorneo().getPoints(pronostico.getTorneo()));
		}
		
		return classifica;
	}
}
