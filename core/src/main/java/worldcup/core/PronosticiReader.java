package worldcup.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PronosticiReader {

	private Map<String, List<PronosticoInput>> pronostici;
	
	public PronosticiReader() {
		
	}
	
	private void init(List<String> pronosticiId) {
		this.pronostici = new HashMap<String, List<PronosticoInput>>();
		for(String pronosticoId: pronosticiId) {
//		String pronosticoId = "1QHIq-ZmhoC3mSlBlAmXjhndZb7Ea33SIcZ3W4olHH2I";
		this.pronostici.put(pronosticoId, leggiPronostico(pronosticoId));
		}
	}
	
	public List<PronosticoInput> leggiPronostico(String pronosticoId) {
		
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
					fos = new FileOutputStream(new File(Costanti.PRONOSTICO_FOLDER, pronosticoId+".csv"));
					for(PronosticoInput p: readResults) {
						fos.write(p.toString().getBytes());
						fos.write("\n".getBytes());
					}
				} catch(IOException ex) {
					System.err.println("Errore durante la scrittura del file:"+ex.getMessage());
				}finally {
					try {if(fos!=null) fos.close();} catch(IOException exx) {}
				}
			}
			

			return readResults;
	}
}
