package worldcup.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PronosticiReader {

	public static Map<String, Pronostico> leggiPronostici() {
		Map<String, Pronostico> pronostici = new HashMap<String, Pronostico>();
		
		Collection<Player> players = Player.getPlayers().values();
		for(Player player: players) {
			pronostici.put(player.getNome(), new Pronostico(player));
		}
		
		return pronostici;
	}
	
	public static List<PronosticoInput> leggiPronostico(String pronosticoId) {
		
			PronosticoReader reader = null;
			List<PronosticoInput> readResults = null;
			try {
				reader = new FileSystemPronosticoReader(pronosticoId);
				readResults = reader.readResults();
			} catch(FileNotFoundException e) {
				reader = new GoogleApiPronosticoReader(pronosticoId);
				readResults = reader.readResults();
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(new File(WorldCupProperties.getInstance().getPronosticiFolder(), pronosticoId+".csv"));
					for(PronosticoInput p: readResults) {
						fos.write(p.toString().getBytes());
						fos.write("\n".getBytes());
					}
				} catch(IOException ex) {
					System.err.println("Errore durante la scrittura del file:"+ex.getMessage());
				}finally {
					try {if(fos!=null) fos.close();} catch(IOException exx) {}
				}
				try {
					reader = new FileSystemPronosticoReader(pronosticoId);	
				} catch (FileNotFoundException fnfe) {
					System.err.println("Errore durante la lettura del file:"+fnfe.getMessage());
					System.err.println("Utente non registrato?");
					readResults = new ArrayList<PronosticoInput>();
				}
				
				
			}
			

			return readResults;
	}
}
