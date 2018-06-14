package worldcup.core;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Pronostico {

	private Player player;
	private Torneo torneo;
	private Map<String, PronosticoInput> pronostico;
	public Pronostico(Player player) {
		this.player = player;
		this.torneo = ExampleTorneoReader.getTorneo();
		this.aggiornaPronostico();
	}

	public void inserisciPronostico(String id, int home, int away) {
		this.torneo.getMatch(id).play(home, away);
		PronosticoInput input = new PronosticoInput();
		input.setId(id);
		input.setHome(home);
		input.setAway(away);
		
		this.pronostico.put(id, input);

		this.salvaPronostico();
		this.aggiornaPronostico();

	}
	
	private void salvaPronostico() {
		PronosticoWriter writer = new PronosticoWriter(this.player.getId());
		List<PronosticoInput> values = this.pronostico.values().stream().collect(Collectors.toList());
		Collections.sort(values);
		writer.write(values);
	}

	private void aggiornaPronostico() {
		this.pronostico = PronosticiReader.leggiPronostico(this.player.getId());
		for(PronosticoInput input: this.pronostico.values()) {
			this.torneo.play(input.getId(), input.getHome(), input.getAway());
		}
	}
	
	public Pronostico() {
	}
	
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public Torneo getTorneo() {
		return torneo;
	}
	public void setTorneo(Torneo torneo) {
		this.torneo = torneo;
	}
}
