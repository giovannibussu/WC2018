package worldcup.core;

import java.util.List;

public class Pronostico {

	private Player player;
	private Torneo torneo;
	
	public Pronostico(Player player) {
		this.player = player;
		this.torneo = ExampleTorneoReader.getTorneo();
		List<PronosticoInput> leggiPronostico = PronosticiReader.leggiPronostico(player.getId());
		for(PronosticoInput input: leggiPronostico) {
			this.torneo.play(input.getId(), input.getHome(), input.getAway());
		}
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
