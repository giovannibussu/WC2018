package worldcup.core;

import java.text.SimpleDateFormat;
import java.util.Date;

import worldcup.core.MatchResult.RISULTATO;

public class Match extends JsonSerializable {

	private Team home;
	private Team away;
	private MatchResult result;
	private boolean played;
	private AbstractSubTorneo torneo;
	private Date dataMatch;
	private int stadium;
	private String matchId;
	public Match(Team a, Team b, AbstractSubTorneo torneo, Date dataMatch, int stadium, String matchId) {
		this.init(a, b, torneo, dataMatch, stadium, matchId);
	}

	public Match(AbstractSubTorneo torneoA, int indexA, AbstractSubTorneo torneoB, int indexB, AbstractSubTorneo torneo, Date dataMatch, int stadium, String matchId) {
		Team a = null;
		Team b = null;
		if(torneoA.isPlayed())
			a = torneoA.getAtPosition(indexA);
		else
			torneoA.registerMatch(this, true, indexA);
		if(torneoB.isPlayed())
			b = torneoB.getAtPosition(indexB);
		else
			torneoB.registerMatch(this, false, indexB);
		
		this.init(a, b, torneo, dataMatch, stadium, matchId);
	}
	public void init(Team a, Team b, AbstractSubTorneo torneo, Date dataMatch, int stadium, String matchId) {
		this.matchId = matchId;
		this.stadium = stadium;
		this.dataMatch = dataMatch;
		this.home = a;
		this.away = b;
		this.torneo = torneo;
		this.torneo.addMatch(this);
	}
	
	public boolean isDraw() {
		if(result!=null)
			return result.getRisultato().equals(RISULTATO.X);
		else
			return false;
	}

	public void play(int home, int away) {
		this.result = new MatchResult(home, away);
		this.tryPlay();
	}
	
	private void tryPlay() {
		if(this.isPlayable() && this.result!=null) {
			this.played = true;
			this.torneo.play(this, this.result.getGoalsHome(), this.result.getGoalsAway());
		}
	}

	public Team getWinner() {
		if(this.result!=null) {
			switch(this.result.getRisultato()) {
			case X: return null;
			case _1:return home;
			case _2:return away;
			}
		}
		return null;
	}

	public Team getLoser() {
		if(this.result!=null) {
			switch(this.result.getRisultato()) {
			case X: return null;
			case _1:return away;
			case _2:return home;
			}
		}
		return null;
	}

	public boolean isPlayed() {
		return this.played;
	}
	
	public boolean isPlayable() {
		return this.home !=null && this.away!=null;
	}
	
	public boolean isWinner(Team team) {
		return team.equals(getWinner());
	}

	public int getGoalsDone(Team team) {
		if(this.result!=null) {
			if(team.equals(home)) {
				return this.result.getGoalsHome();
			} else if(team.equals(away)) {
				return this.result.getGoalsAway();
			} else throw new RuntimeException();
		}
		return 0;
	}
	public int getGoalsTaken(Team team) {
		if(this.result!=null) {
			if(team.equals(home)) {
				return this.result.getGoalsAway();
			} else if(team.equals(away)) {
				return this.result.getGoalsHome();
			} else throw new RuntimeException();
		}
		return 0;
	}

	public Team getHome() {
		return this.home;
	}


	public Team getAway() {
		return this.away;
	}

	public void setHome(Team home) {
		this.home = home;
		this.tryPlay();
	}

	public void setAway(Team away) {
		this.away = away;
		this.tryPlay();
	}

	public MatchResult getResult() {
		return result;
	}

	public boolean equi(Match other) {
		if(this.home == null)
			return false;
		if(this.away == null)
			return false;
		if(other.getHome() == null)
			return false;
		if(other.getAway() == null)
			return false;
		
		if(!(this.getTorneo().getType().equals(other.getTorneo().getType()))) {
			return false;
		}
		
		if(other.getHome().equals(this.getHome()) && other.getAway().equals(this.getAway())) {
			return true;
		}
		if(other.getAway().equals(this.getHome()) && other.getHome().equals(this.getAway())) {
			return true;
		}
		return false;
	}

	public Date getDataMatch() {
		return dataMatch;
	}
	
	public String getDataMatchAsString() {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE dd/MM");
		return sdf.format(dataMatch);
	}
	
	public String getOraMatchAsString() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(dataMatch);
	}

	public AbstractSubTorneo getTorneo() {
		return torneo;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if(this.getHome()!=null) {
			sb.append(this.getHome().getId());
		} else {
			sb.append("--");
		}
		sb.append(" VS ");
		
		if(this.getAway()!=null) {
			sb.append(this.getAway().getId());
		} else {
			sb.append("--");
		}
		
		if(this.isPlayed()) {
			sb.append(": ");
			sb.append(this.result.getGoalsHome());
			sb.append("-");
			sb.append(this.result.getGoalsAway());
		}

		return sb.toString();
	}

	public int getStadium() {
		return stadium;
	}

	public String getMatchId() {
		return matchId;
	}
}
