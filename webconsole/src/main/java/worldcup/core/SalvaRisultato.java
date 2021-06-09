package worldcup.core;

import java.io.File;
import java.util.Optional;

import worldcup.clients.api.TorneoApi;
import worldcup.clients.impl.ApiClient;
import worldcup.clients.impl.PatchedApiClient;
import worldcup.clients.model.Partita;
import worldcup.clients.model.Pronostico;
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
	
	public Partita setResult(String username, String password, String idPartita, int goalHome, int goalAway) throws Exception {
		try {
			this.username = username;
			this.password = password;
			
			RisultatoPartita risultatoPartita = new RisultatoPartita();
			risultatoPartita.setGoalCasa(goalHome);
			risultatoPartita.setGoalTrasferta(goalAway);
	
			ApiClient client = new PatchedApiClient(Optional.of(this.username), Optional.of(this.password));
			client.setBasePath(TorneoConfig.API_BASE_URL); //TODO properties
			this.torneoApi = new TorneoApi(client);
			
			return this.torneoApi.updateRisultatoPartita(this.idTorneo, idPartita, risultatoPartita );
		} catch (Exception e) {
			System.err.println("Errore setResult torneo["+this.idTorneo+"] partita["+idPartita+"] GoalCasa[ " + goalHome+"] GoalAway [" + goalAway+"]: "+ e.getMessage());
			e.printStackTrace(System.err);
			throw e;
		}
	}

	public boolean login(String username, String password) {
		this.username = username;
		this.password = password;
		
		Gioco gioco = new Gioco();
		return gioco.check(username, password);
	}
	
	public Pronostico inviaPronostico(String username, String password, String idGiocatore, File pronostico) throws Exception{
		try {
			this.username = username;
			this.password = password;
			
			ApiClient client = new PatchedApiClient(Optional.of(this.username), Optional.of(this.password));
			client.setBasePath(TorneoConfig.API_BASE_URL); //TODO properties
			this.torneoApi = new TorneoApi(client);
			
			return this.torneoApi.postPronostico(this.idTorneo, idGiocatore, pronostico);
		} catch (Exception e) {
			System.err.println("Errore setResult torneo["+this.idTorneo+"] idGiocatore["+idGiocatore+"]: "+ e.getMessage());
			e.printStackTrace(System.err);
			throw e;
		}
	}
}
