package worldcup.core;

public class MatchResult {

	public enum RISULTATO {_1,X,_2}
	
	private RISULTATO risultato;
	private String risultatoEsatto;
	private Integer goalsHome;
	private Integer goalsAway;

	public MatchResult(int home, int away) {
		this.risultatoEsatto = home+"-"+away;
		this.risultato= (home==away) ? RISULTATO.X : (home>away)? RISULTATO._1 : RISULTATO._2;
		this.goalsHome = home;
		this.goalsAway= away;
	}
	public RISULTATO getRisultato() {
		return risultato;
	}
	public String getRisultatoEsatto() {
		return risultatoEsatto;
	}
	public Integer getGoalsHome() {
		return goalsHome;
	}
	public Integer getGoalsAway() {
		return goalsAway;
	}
}
