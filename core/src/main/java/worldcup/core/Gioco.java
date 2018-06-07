package worldcup.core;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

	//	public List<Match> matchPerData(Date dataInizio, Date dataFine) {
	//		return ufficiale.getTorneo().getMatches().values().stream().filter(a-> 
	//			a.getDataMatch().before(dataFine) && a.getDataMatch().after(dataInizio)
	//		).collect(Collectors.toList());
	//	}

	public List<Match> matchPerData(Date dataInizio, Date dataFine) {

		List<Match> list = new ArrayList<>();

		Team squadra1 = new Team();
		squadra1.setId("RUS");
		squadra1.setNome("Russia");
		squadra1.setBandiera("images/russia.png");
		Team squadra2 = new Team();
		squadra2.setId("ARS");
		squadra2.setNome("Arabia Saudita");
		squadra2.setBandiera("images/arabia_saudita.png");

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

		Torneo torneo = ExampleTorneoReader.getTorneo();
		Date date = new Date();
		try {
			date = sdf.parse("14-06-2018 18:00");
		} catch(Exception e) {

		} 
		Match p1 = new Match(squadra1, squadra2,torneo.getAbstractSubTorneo("A"), date, 1,"a");
		list.add(p1);
		return list;

	}
	
	public Match getMatch(String idMatch) {
		return matchPerData(null, null).get(0);
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
