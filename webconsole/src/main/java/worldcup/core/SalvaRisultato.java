package worldcup.core;

import java.util.Optional;

import worldcup.clients.api.TorneoApi;
import worldcup.clients.impl.ApiClient;
import worldcup.clients.impl.PatchedApiClient;
import worldcup.clients.model.RisultatoPartita;
import worldcup.core.utils.TorneoConfig;

public class SalvaRisultato {

	private TorneoApi torneoApi = null;
	private String idTorneo = null;
	
	private String username = null;
	private String password;
	
	public SalvaRisultato() {
		this.idTorneo =  TorneoConfig.ID_TORNEO_DEFAULT;
	}
	
	public void setResult(String idPartita, int goalHome, int goalAway) {
		try {
			RisultatoPartita risultatoPartita = new RisultatoPartita();
			risultatoPartita.setGoalCasa(goalHome);
			risultatoPartita.setGoalTrasferta(goalAway);
	
			ApiClient client = new PatchedApiClient(Optional.of(this.username), Optional.of(this.password));
			client.setBasePath(TorneoConfig.API_BASE_URL); //TODO properties
			this.torneoApi = new TorneoApi(client);
			
			this.torneoApi.updateRisultatoPartita(this.idTorneo, idPartita, risultatoPartita );
		} catch (Exception e) {
			System.err.println("Errore setResult torneo["+this.idTorneo+"] partita["+idPartita+"] GoalCasa[ " + goalHome+"] GoalAway [" + goalAway+"]: "+ e.getMessage());
			e.printStackTrace(System.err);
		}
	}

	public boolean login(String username, String password) {
		this.username = username;
		this.password = password;
		
		Gioco gioco = new Gioco();
		return gioco.check(username, password);
	}
}
