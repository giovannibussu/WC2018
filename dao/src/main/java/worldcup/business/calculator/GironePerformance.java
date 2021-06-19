package worldcup.business.calculator;

import worldcup.orm.vo.SquadraVO;

public class GironePerformance {

	private SquadraVO squadra;
	private int punti = 0;
	private int goalFatti = 0;
	private int goalSubiti = 0;
	private int fairPlay = 0;
	private String girone;

	public SquadraVO getSquadra() {
		return squadra;
	}
	public void setSquadra(SquadraVO squadra) {
		this.squadra = squadra;
	}
	public int getPunti() {
		return punti;
	}
	public void setPunti(int punti) {
		this.punti = punti;
	}
	public int getGoalFatti() {
		return goalFatti;
	}
	public void setGoalFatti(int goalFatti) {
		this.goalFatti = goalFatti;
	}
	public int getGoalSubiti() {
		return goalSubiti;
	}
	public int getDifferenzaReti() {
		return goalFatti - goalSubiti;
	}
	public void setGoalSubiti(int goalSubiti) {
		this.goalSubiti = goalSubiti;
	}
	public int getFairPlay() {
		return fairPlay;
	}
	public void setFairPlay(int fairPlay) {
		this.fairPlay = fairPlay;
	}
	
	public void addPunti(int punti) {
		this.punti += punti;
	}
	
	public void addGoalFatti(int goalFatti) {
		this.goalFatti += goalFatti;
	}
	
	public void addGoalSubiti(int goalSubiti) {
		this.goalSubiti += goalSubiti;
	}
	public String getGirone() {
		return girone;
	}
	public void setGirone(String girone) {
		this.girone = girone;
	}
}
