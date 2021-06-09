package worldcup.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import worldcup.clients.api.TorneoApi;
import worldcup.clients.impl.ApiClient;
import worldcup.clients.impl.PatchedApiClient;
import worldcup.clients.model.Pronostico;

public class ClassificaGenerale {

//	private Gioco gioco = null;
	private TorneoApi torneoApi = null;
	private String idTorneo = null;
	
	public ClassificaGenerale() {
//		this.gioco = new Gioco();
		ApiClient client = new PatchedApiClient(Optional.empty(), Optional.empty());
		client.setBasePath("http://127.0.0.1:8080/api-worldcup/api/v1"); //TODO properties
		this.torneoApi = new TorneoApi(client);
		
		this.idTorneo = "EURO2021"; //TODO properties
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
}
