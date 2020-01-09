package worldcup.core;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import worldcup.core.utils.FileSystemPronosticoReader;
import worldcup.core.utils.GoogleApiPronosticoReader;

public class PronosticiReader {

	public static Map<String, Pronostico> leggiPronostici() {
		Map<String, Pronostico> pronostici = new HashMap<String, Pronostico>();
		
		Collection<Player> players = Player.getPlayers().values();
		for(Player player: players) {
			pronostici.put(player.getNome(), new Pronostico(player));
		}
		
		return pronostici;
	}
	
	public static Map<String, PronosticoInput> leggiPronostico(String pronosticoId) {
		
			PronosticoReader reader = null;
			Map<String, PronosticoInput> readResults = null;
			try {
				reader = new FileSystemPronosticoReader(pronosticoId);
				readResults = reader.readResults();
			} catch(FileNotFoundException e) {
				reader = new GoogleApiPronosticoReader(pronosticoId);
				
				PronosticoWriter writer = new PronosticoWriter(pronosticoId);
				readResults = reader.readResults();
				writer.write(readResults.values());

				try {
					reader = new FileSystemPronosticoReader(pronosticoId);	
				} catch (FileNotFoundException fnfe) {
					System.err.println("Errore durante la lettura del file:"+fnfe.getMessage());
					System.err.println("Utente non registrato?");
					readResults = new HashMap<String, PronosticoInput>();
				}
				
				
			}
			

			return readResults;
	}
}
