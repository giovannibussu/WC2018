package worldcup.core;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import worldcup.clients.api.TorneoApi;
import worldcup.clients.impl.ApiClient;
import worldcup.clients.impl.PatchedApiClient;
import worldcup.core.model.Match;
import worldcup.core.model.Player;

public class ProssimiIncontri {
	
	private Gioco gioco = null; //TODO commentare e implemetare con API
	private TorneoApi torneoApi = null;
	private String idTorneo = null;
	
	public ProssimiIncontri() {
	this.gioco = new Gioco();
	ApiClient client = new PatchedApiClient(Optional.empty(), Optional.empty());
	client.setBasePath("http://127.0.0.1:8080/api-worldcup/api/v1"); //TODO properties
	this.torneoApi = new TorneoApi(client);
	
	this.idTorneo = "idTorneo"; //TODO properties

	}
	
	public List<Match> getListProssimiIncontri(){
		Calendar nowCal = new GregorianCalendar();
		nowCal.set(Calendar.HOUR_OF_DAY, 0);
		nowCal.set(Calendar.MINUTE, 0);
		Date now = nowCal.getTime();
		nowCal.add(Calendar.DATE, 2);
		Date tomorrow= nowCal.getTime();
		return this.gioco.matchPerData(now, tomorrow);
	}
	
	public Match getMatch(String idMatch) {
		return this.gioco.getMatch(idMatch);
	}
	
	public Map<Player, Match> getPronosticiPerMatch(Match match) {
		return this.gioco.pronosticiPerMatch(match);
	}
	
	public List<Match> getListaMatch(){
		return this.gioco.getMatchList();
	}
	
	public void setResult(Match match, int goalHome, int goalAway) {
		this.gioco.setResult(match, goalHome, goalAway);
	}
	
	public boolean login(String username, String password) {
		return this.gioco.check(username, password);
	}
	
	public Grafico distribuzionePronosticiPerMatchRisultatoEsatto(Match match) {
		return this.gioco.distribuzionePronosticiPerMatchRisultatoEsatto(match);
	}
	
	public Grafico distribuzionePronosticiPerMatchRisultato(Match match) {
		return this.gioco.distribuzionePronosticiPerMatchRisultato(match);
	}
}
