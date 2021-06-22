package worldcup.business.calculator;

import java.util.HashMap;
import java.util.Map;

import worldcup.orm.vo.SquadraVO;

public class GironePerformance {

	private SquadraVO squadra;
	private Map<String, GironeSingleMatchPerformance> performances = new HashMap<String, GironeSingleMatchPerformance>();
	
	private String girone;

	public SquadraVO getSquadra() {
		return squadra;
	}
	public void setSquadra(SquadraVO squadra) {
		this.squadra = squadra;
	}
	public int getPunti() {
		return this.performances.values().stream().mapToInt(a-> a.getPunti()).sum();
	}
	public int getGoalFatti() {
		return this.performances.values().stream().mapToInt(a-> a.getGoalFatti()).sum();
	}
	public int getGoalSubiti() {
		return this.performances.values().stream().mapToInt(a-> a.getGoalSubiti()).sum();
	}
	public int getDifferenzaReti() {
		return getGoalFatti() - getGoalSubiti();
	}
	public int getFairPlay() {
		return squadra.getFairPlay();
	}
	public int getRankingFifa() {
		return squadra.getRankingFifa();
	}
	public String getGirone() {
		return girone;
	}
	public void setGirone(String girone) {
		this.girone = girone;
	}
	public int getVittorie() {
		return this.performances.values().stream().mapToInt(a-> a.getVittorie()).sum();
	}
	public Map<String, GironeSingleMatchPerformance> getPerformances() {
		return performances;
	}
}
