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
	}
	
	public Partita setResult(String username, String password, String idPartita, int goalHome, int goalAway) throws Exception {
		try {
			this.username = username;
			this.password = password;
			this.idTorneo =  TorneoConfig.getInstance().getIdTorneo();
			
			RisultatoPartita risultatoPartita = new RisultatoPartita();
			risultatoPartita.setGoalCasa(goalHome);
			risultatoPartita.setGoalTrasferta(goalAway);
	
			ApiClient client = new PatchedApiClient(Optional.of(this.username), Optional.of(this.password));
			client.setBasePath(TorneoConfig.getInstance().getApiBaseUrl());
			this.torneoApi = new TorneoApi(client);
			
			return this.torneoApi.updateRisultatoPartita(this.idTorneo, idPartita, risultatoPartita );
		} catch (Exception e) {
			System.err.println("Errore setResult torneo["+this.idTorneo+"] partita["+idPartita+"] GoalCasa[ " + goalHome+"] GoalAway [" + goalAway+"]: "+ e.getMessage());
			e.printStackTrace(System.err);
			throw e;
		}
	}

	public Pronostico inviaPronostico(String username, String password, String idGiocatore, File pronostico, String linkPronostico) throws Exception{
		try {
			this.username = username;
			this.password = password;
			this.idTorneo =  TorneoConfig.getInstance().getIdTorneo();
			
			ApiClient client = new PatchedApiClient(Optional.of(this.username), Optional.of(this.password));
			client.setBasePath(TorneoConfig.getInstance().getApiBaseUrl());
			this.torneoApi = new TorneoApi(client);
			
			return this.torneoApi.postPronostico(this.idTorneo, idGiocatore, linkPronostico, pronostico);
		} catch (Exception e) {
			System.err.println("Errore setResult torneo["+this.idTorneo+"] idGiocatore["+idGiocatore+"]: "+ e.getMessage());
			e.printStackTrace(System.err);
			throw e;
		}
	}
	
	public Partita cancellaRisultato(String username, String password, String idPartita) throws Exception {
		try {
			this.username = username;
			this.password = password;
			this.idTorneo =  TorneoConfig.getInstance().getIdTorneo();
			
			ApiClient client = new PatchedApiClient(Optional.of(this.username), Optional.of(this.password));
			client.setBasePath(TorneoConfig.getInstance().getApiBaseUrl());
			this.torneoApi = new TorneoApi(client);
			
			return this.torneoApi.deleteRisultatoPartita(this.idTorneo, idPartita);
		} catch (Exception e) {
			System.err.println("Errore setResult torneo["+this.idTorneo+"] partita["+idPartita+"]: "+ e.getMessage());
			e.printStackTrace(System.err);
			throw e;
		}
	}
}
