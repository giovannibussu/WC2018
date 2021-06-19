package worldcup.core;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import worldcup.clients.api.TorneoApi;
import worldcup.clients.impl.ApiClient;
import worldcup.clients.impl.PatchedApiClient;
import worldcup.clients.model.Grafico;
import worldcup.clients.model.OrderType;
import worldcup.clients.model.Partita;
import worldcup.clients.model.PronosticoPartita;
import worldcup.clients.model.TipoDistribuzione;
import worldcup.core.utils.TorneoConfig; 

public class ProssimiIncontri {

	public static final Integer NUMERO_PARTITE_HOME = 6;
	
	private TorneoApi torneoApi = null;
	private String idTorneo = null;

	public ProssimiIncontri() throws Exception {
		ApiClient client = new PatchedApiClient(Optional.empty(), Optional.empty());
		client.setBasePath(TorneoConfig.getInstance().getApiBaseUrl());
		this.torneoApi = new TorneoApi(client);
		this.idTorneo =  TorneoConfig.getInstance().getIdTorneo(); 
	}

	public List<Partita> getListProssimiIncontri(){
//		Calendar nowCal = new GregorianCalendar();
//		nowCal.set(Calendar.HOUR_OF_DAY, 0);
//		nowCal.set(Calendar.MINUTE, 0);
//		Date now = nowCal.getTime();
//		nowCal.add(Calendar.DATE, 6);
//		Date tomorrow= nowCal.getTime();
//
		String start = null;//new DateTime(now);
		String end = null; //new DateTime(tomorrow);
		try {
			List<Partita> matchPerData =  this.torneoApi.listPartite(this.idTorneo, NUMERO_PARTITE_HOME, 0l, start, end, true, OrderType.ASC);
//			// visualizzo un numero di partite uguali al minimo tra quelle trovate e il max visualizzabile in pagina
//			int maxPartite = Math.min(matchPerData.size(), NUMERO_PARTITE_HOME);
//						
//			return maxPartite < matchPerData.size() ? matchPerData.subList(0, maxPartite) : matchPerData;
			return matchPerData;
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

	public List<Partita> getListaMatch(){   
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		
		Calendar startCal = new GregorianCalendar();
		startCal.set(Calendar.HOUR_OF_DAY, 0);
		startCal.set(Calendar.MINUTE, 0);
		startCal.set(Calendar.YEAR, 2021);
		startCal.set(Calendar.MONTH, 5);
		startCal.set(Calendar.DATE, 10);
		Date now = startCal.getTime();
		
		String start = formatter.format(now);
		
		Calendar endCal = new GregorianCalendar();
		endCal.set(Calendar.HOUR_OF_DAY, 0);
		endCal.set(Calendar.MINUTE, 0);
		endCal.set(Calendar.YEAR, 2021);
		endCal.set(Calendar.MONTH, 6);
		endCal.set(Calendar.DATE, 15);
		Date tomorrow = endCal.getTime();
		
		String end = formatter.format(tomorrow);

		try {
			List<Partita> matchPerData =  this.torneoApi.listPartite(this.idTorneo, NUMERO_PARTITE_HOME, 0l, start, end, true, OrderType.ASC);
			return matchPerData;
		} catch (Exception e) {
			System.err.println("Errore getListProssimiIncontri torneo["+this.idTorneo+"]: "+ e.getMessage());
			e.printStackTrace(System.err);
		}
		return new ArrayList<>();
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
	
	public List<Partita> getListaMatchStorico(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		
		Calendar startCal = new GregorianCalendar();
		startCal.set(Calendar.HOUR_OF_DAY, 0);
		startCal.set(Calendar.MINUTE, 0);
		startCal.set(Calendar.YEAR, 2021);
		startCal.set(Calendar.MONTH, 5);
		startCal.set(Calendar.DATE, 10);
		Date now = startCal.getTime();
		
		String start = formatter.format(now);
		
		Calendar endCal = new GregorianCalendar();
		endCal.set(Calendar.HOUR_OF_DAY, 0);
		endCal.set(Calendar.MINUTE, 0);
		endCal.set(Calendar.YEAR, 2021);
		endCal.set(Calendar.MONTH, 6);
		endCal.set(Calendar.DATE, 15);
		Date tomorrow = endCal.getTime();
		
		String end = formatter.format(tomorrow);
		
		try {
			List<Partita> matchPerData =  this.torneoApi.listPartite(this.idTorneo, Integer.MAX_VALUE, 0l, start, end, false, OrderType.DESC);
			return matchPerData;
		} catch (Exception e) {
			System.err.println("Errore getListProssimiIncontri torneo["+this.idTorneo+"]: "+ e.getMessage());
			e.printStackTrace(System.err);
		}
		return new ArrayList<>();
	}
}
