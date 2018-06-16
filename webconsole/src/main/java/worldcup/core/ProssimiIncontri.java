package worldcup.core;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

public class ProssimiIncontri {
	
	private Gioco gioco = null;
	
	public ProssimiIncontri() {
	this.gioco = new Gioco();
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
}
