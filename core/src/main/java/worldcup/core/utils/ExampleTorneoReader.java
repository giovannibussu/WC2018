package worldcup.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import worldcup.core.AbstractSubTorneo;
import worldcup.core.AbstractSubTorneo.TYPE;
import worldcup.core.model.Girone;
import worldcup.core.model.Knockout;
import worldcup.core.model.Match;
import worldcup.core.model.Team;
import worldcup.core.model.Torneo;

/**
 * Hello world!
 *
 */
public class ExampleTorneoReader 
{
	public static Torneo getTorneo(String playerId) {

		int nTeamGirone = 4;
		int nTeamPassaggioTurnoGirone = 2;

		Map<String, Girone> gironi = new HashMap<String, Girone>();
		Map<String, Match> matches = new HashMap<String, Match>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy.HH:mm");

		try {
			Map<String, Team> teams = Team.readTeams(playerId);

			InputStream isGironi= ExampleTorneoReader.class.getResourceAsStream("/gironi.txt");
			BufferedReader breader = new BufferedReader(new InputStreamReader(isGironi));
			
			List<String> lines = breader.lines().collect(Collectors.toList());
			
			breader.close();

			for(String line: lines) {
				String[] split = line.trim().split(";");
				String home = split[3];
				String away = split[4];
				String idMatch = split[0];
				String girone = split[1];
				String date = split[2];
				int stadium = Integer.parseInt(split[5]);
				int giornata = Integer.parseInt(split[6]);
				
				
				if(!teams.containsKey(home)) {
					throw new RuntimeException("Squadra ["+home+"] non censita");
				}
				Team homeTeam = teams.get(home);
				
				if(!teams.containsKey(away)) {
					throw new RuntimeException("Squadra ["+away+"] non censita");
				}
				Team awayTeam = teams.get(away);
				
				if(!gironi.containsKey(girone)) {
					gironi.put(girone, new Girone(girone, nTeamGirone, nTeamPassaggioTurnoGirone));
				}
				
				
				matches.put(idMatch, new Match(homeTeam, awayTeam, gironi.get(girone), sdf.parse(date), stadium, idMatch, "Fase a gironi - Gruppo " + girone + " - Giornata "+giornata+"/"+(nTeamGirone-1)));
			}

			
		} catch (Exception e) {
			System.err.println(e);
		}
		
		String winner = null;
		try {
			InputStream isKnockout= ExampleTorneoReader.class.getResourceAsStream("/knockout.txt");
			BufferedReader breader = new BufferedReader(new InputStreamReader(isKnockout));
			
			List<String> lines = breader.lines().collect(Collectors.toList());
			
			breader.close();

			Map<String, Knockout> knockouts = new HashMap<String, Knockout>();

			for(String line: lines) {
				String[] split = line.trim().split(";");
				Date date = sdf.parse(split[0].trim());
				int stadium = Integer.parseInt(split[4]);
				String knockoutPhase = split[1];

				String[] game = split[2].split("-");
				String[] home = game[0].split("_");
				
				int posHome = Integer.parseInt(home[0]);
				String previousHome = home[1];
				String[] away = game[1].split("_");
				int posAway = Integer.parseInt(away[0]);
				String previousAway = away[1];

				String gameNumber = split[3].trim();
				
				String descrizione = null;
				if(knockoutPhase.equals("OTT")) {
					knockouts.put(gameNumber, new Knockout(gameNumber, TYPE.OTTAVI));
					descrizione = "Ottavi di finale";
				} else if (knockoutPhase.equals("QUA")) {
					knockouts.put(gameNumber, new Knockout(gameNumber, TYPE.QUARTI));
					descrizione = "Quarti di finale";
				} else if (knockoutPhase.equals("SEM")) {
					knockouts.put(gameNumber, new Knockout(gameNumber, TYPE.SEMIFINALI));
					descrizione = "Semifinali";
				} else if (knockoutPhase.equals("FIN")) {
					knockouts.put(gameNumber, new Knockout(gameNumber, TYPE.FINALE));
					descrizione = "Finale";
					winner = gameNumber;
				}
				AbstractSubTorneo prevoiusHomeP = null;				
				try {
					Integer.parseInt(previousHome);
					prevoiusHomeP = knockouts.get(previousHome);
				} catch(Exception e) {
					prevoiusHomeP = gironi.get(previousHome);
				}

				AbstractSubTorneo prevoiusAwayP = null;				
				try {
					Integer.parseInt(previousAway);
					prevoiusAwayP = knockouts.get(previousAway);
				} catch(Exception e) {
					prevoiusAwayP = gironi.get(previousAway);
				}

				matches.put(gameNumber, new Match(prevoiusHomeP, posHome-1, prevoiusAwayP, posAway-1, knockouts.get(gameNumber), date, stadium, gameNumber, descrizione));
			}

			
		} catch (ParseException | IOException e) {
			System.err.println(e);
		}
		
		return new Torneo(matches, winner);
	}

	
}
