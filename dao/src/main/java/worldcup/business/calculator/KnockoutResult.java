package worldcup.business.calculator;

import java.util.HashMap;
import java.util.Map;

import worldcup.orm.vo.SquadraVO;

public class KnockoutResult {

	private Map<String, SquadraVO> winners;
	private Map<String, SquadraVO> losers;

	public KnockoutResult() {
		this.winners = new HashMap<>();
		this.losers = new HashMap<>();
	}
	
	public void setWinner(String codicePartita, SquadraVO squadra) {
		this.winners.put(codicePartita, squadra);
	}
	
	public void setLoser(String codicePartita, SquadraVO squadra) {
		this.losers.put(codicePartita, squadra);
	}

	public Map<String, SquadraVO> getWinners() {
		return winners;
	}

	public Map<String, SquadraVO> getLosers() {
		return losers;
	}

}
