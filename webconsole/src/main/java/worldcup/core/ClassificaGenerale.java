package worldcup.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import worldcup.clients.api.TorneoApi;
import worldcup.clients.impl.ApiClient;
import worldcup.clients.impl.PatchedApiClient;
import worldcup.clients.model.Partita;
import worldcup.clients.model.Pronostico;
import worldcup.core.utils.TorneoConfig;

public class ClassificaGenerale {

	private TorneoApi torneoApi = null;
	private String idTorneo = null;
	
	public ClassificaGenerale() throws Exception {
		ApiClient client = new PatchedApiClient(Optional.empty(), Optional.empty());
		client.setBasePath(TorneoConfig.getInstance().getApiBaseUrl());
		this.torneoApi = new TorneoApi(client);
		this.idTorneo =  TorneoConfig.getInstance().getIdTorneo();
	}

	public List<Pronostico> getClassificaGenerale(String categoria) {

		try {
			return this.torneoApi.getClassifica(this.idTorneo, categoria);
			
		} catch (Exception e) {
			System.err.println("Errore getClassifica torneo["+this.idTorneo+"] categoria ["+categoria+"]:" + e.getMessage());
			e.printStackTrace(System.err);
		}
		return new ArrayList<Pronostico>();
	}
	
	public List<Pronostico> getPronostici() {
		try {
			List<Pronostico> classifica = this.torneoApi.getPronostici(this.idTorneo);
			return classifica;
		} catch (Exception e) {
			System.err.println("Errore getPronostici torneo["+this.idTorneo+"]: "+ e.getMessage());
			e.printStackTrace(System.err);
		}

		return new ArrayList<Pronostico>();
	}
	
	public Pronostico getPronosticoUfficiale() {
		try {
			Pronostico classifica = this.torneoApi.getPronosticoUfficiale(this.idTorneo);
			
			classifica.getPartite().sort(ClassificaGenerale::compareByIdPartita);
			
			return classifica;
		} catch (Exception e) {
			System.err.println("Errore getPronosticoUfficiale torneo["+this.idTorneo+"]: "+ e.getMessage());
			e.printStackTrace(System.err);
		}

		return null;
	}
	
	public Pronostico getPronosticoGiocatore(String idGiocatore) {
		try {
			Pronostico classifica = this.torneoApi.getPronostico(this.idTorneo, idGiocatore);
			
			classifica.getPartite().sort(ClassificaGenerale::compareByIdPartita);
			
			return classifica;
		} catch (Exception e) {
			System.err.println("Errore getPronosticoGiocatore torneo["+this.idTorneo+"] giocatore["+idGiocatore+"]: "+ e.getMessage());
			e.printStackTrace(System.err);
		}

		return null;
	}
	
	public static int compareByIdPartita(Partita lhs, Partita rhs) {
        return Integer.compare(Integer.parseInt(lhs.getIdPartita()), Integer.parseInt(rhs.getIdPartita()));
	}
}
