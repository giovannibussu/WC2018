package worldcup.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.joda.time.DateTime;

import worldcup.clients.api.TorneoApi;
import worldcup.clients.impl.ApiClient;
import worldcup.clients.impl.PatchedApiClient;
import worldcup.clients.model.Grafico;
import worldcup.clients.model.Partita;
//import worldcup.core.model.Match;
//import worldcup.core.model.Player;
import worldcup.clients.model.PronosticoPartita;
import worldcup.clients.model.TipoDistribuzione; 

public class ProssimiIncontri {

	//	private Gioco gioco = null; //TODO commentare e implemetare con API
	private TorneoApi torneoApi = null;
	private String idTorneo = null;

	public ProssimiIncontri() {
		//	this.gioco = new Gioco();
		ApiClient client = new PatchedApiClient(Optional.empty(), Optional.empty());
		client.setBasePath("http://127.0.0.1:8080/api-worldcup/api/v1"); //TODO properties
		this.torneoApi = new TorneoApi(client);

		this.idTorneo = "idTorneo"; //TODO properties

	}

	public List<Partita> getListProssimiIncontri(){
		Calendar nowCal = new GregorianCalendar();
		nowCal.set(Calendar.HOUR_OF_DAY, 0);
		nowCal.set(Calendar.MINUTE, 0);
		Date now = nowCal.getTime();
		nowCal.add(Calendar.DATE, 2);
		Date tomorrow= nowCal.getTime();

		DateTime start = new DateTime(now);
		DateTime end = new DateTime(tomorrow);
		try {
			return this.torneoApi.listPartite(this.idTorneo, null, null, start, end);
		} catch (Exception e) {
			System.err.println("Errore getListProssimiIncontri torneo["+this.idTorneo+"]: "+ e.getMessage());
			e.printStackTrace(System.err);
		}
		return new ArrayList<>();
	}

	public Partita getMatch(String idMatch) {
		try {
			return this.torneoApi.getPartita(this.idTorneo, idMatch);
		} catch (Exception e) {
			System.err.println("Errore getMatch torneo["+this.idTorneo+"]  partita["+idMatch+"]: "+ e.getMessage());
			e.printStackTrace(System.err);
		}
		return null;
	}

	public List<PronosticoPartita> getPronosticiPerMatch(String idPartita) {
		try {
			return this.torneoApi.getPronosticiPartita(this.idTorneo, idPartita);
		} catch (Exception e) {
			System.err.println("Errore getPronosticiPerMatch torneo["+this.idTorneo+"] partita["+idPartita+"]: "+ e.getMessage());
			e.printStackTrace(System.err);
		}
		return null;
		//		return this.gioco.pronosticiPerMatch(match);
	}

	public List<Partita> getListaMatch(){  //TODO Bussu
		//		return this.torneoApi.listaPartiteDaRegistrare();


		return null;
	}

	public Grafico distribuzionePronosticiPerMatchRisultatoEsatto(String idPartita) {
		try {
			return this.torneoApi.getDistribuzionePartita(this.idTorneo, idPartita, TipoDistribuzione._1X2);
		} catch (Exception e) {
			System.err.println("Errore distribuzionePronosticiPerMatchRisultatoEsatto torneo["+this.idTorneo+"]  partita["+idPartita+"]: "+ e.getMessage());
			e.printStackTrace(System.err);
		}
		return null;
		
//		return this.gioco.distribuzionePronosticiPerMatchRisultatoEsatto(match);
	}

	public Grafico distribuzionePronosticiPerMatchRisultato(String idPartita) {
		try {
			return this.torneoApi.getDistribuzionePartita(this.idTorneo, idPartita, TipoDistribuzione.RISULTATO);
		} catch (Exception e) {
			System.err.println("Errore distribuzionePronosticiPerMatchRisultato torneo["+this.idTorneo+"]  partita["+idPartita+"]: "+ e.getMessage());
			e.printStackTrace(System.err);
		}
		return null;
		
//		return this.gioco.distribuzionePronosticiPerMatchRisultato(match);
	}
}
