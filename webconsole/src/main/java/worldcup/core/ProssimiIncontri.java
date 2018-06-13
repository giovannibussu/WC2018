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
		Date now = nowCal.getTime();
		nowCal.add(Calendar.DATE, 1);
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
	

}
