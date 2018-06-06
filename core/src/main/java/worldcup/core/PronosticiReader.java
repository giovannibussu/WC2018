package worldcup.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PronosticiReader {

	private List<String> pronosticiId;
	
	public PronosticiReader() {
		this.pronosticiId = new ArrayList<String>();
		this.pronosticiId.add("1QHIq-ZmhoC3mSlBlAmXjhndZb7Ea33SIcZ3W4olHH2I");
	}
	
	public void leggiPronostici() throws IOException {
		boolean lettoDaFile = false;
		
		for(String k: this.pronosticiId) {
			List<PronosticoInput> pronostici = null;
			PronosticoReader reader = null;
			try {
				reader = new FileSystemPronosticoReader(k);
				lettoDaFile = true;
			} catch(FileNotFoundException e) {
				reader = new GoogleApiPronosticoReader(k);
			}
			
			List<PronosticoInput> readResults = reader.readResults();
			
			if(!lettoDaFile) {
				FileOutputStream fos = new FileOutputStream(new File(Costanti.PRONOSTICO_FOLDER, k+".csv"));
				for(PronosticoInput p: readResults) {
					fos.write(p.toString().getBytes());
					fos.write("\n".getBytes());
				}
				
				fos.close();
			}
			
			System.out.println(readResults);
		}
	}
	

	public static void main(String[] args) throws IOException {
		new PronosticiReader().leggiPronostici();
	}
}
