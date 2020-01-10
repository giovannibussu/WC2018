package worldcup.core;

import java.util.List;

import worldcup.core.model.Match;

public class GiocoTest {

	public static void main(String[] args) {
		Gioco gioco = new Gioco();
		
		List<Match> matchs = gioco.getMatchList();
		
		for(Match match: matchs) {
			System.out.println("Match.getid: " + match.getMatchId());
		}
	}
}
