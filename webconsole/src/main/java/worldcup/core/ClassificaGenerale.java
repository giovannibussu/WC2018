package worldcup.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import worldcup.clients.api.TorneoApi;
import worldcup.clients.impl.ApiClient;
import worldcup.clients.impl.PatchedApiClient;
import worldcup.clients.model.Pronostico;
import worldcup.core.utils.TorneoConfig;

public class ClassificaGenerale {

	private TorneoApi torneoApi = null;
	private String idTorneo = null;
	
	public ClassificaGenerale() {
		ApiClient client = new PatchedApiClient(Optional.empty(), Optional.empty());
//		client.setBasePath(TorneoConfig.API_BASE_URL); //TODO properties
		client.setHost("127.0.0.1");
		client.setPort(8081);
		client.setBasePath("/api-worldcup/api/v1");

		this.torneoApi = new TorneoApi(client);
		
		this.idTorneo =  TorneoConfig.ID_TORNEO_DEFAULT;
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
