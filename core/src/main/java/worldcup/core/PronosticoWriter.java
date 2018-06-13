package worldcup.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

public class PronosticoWriter {

	private String pronosticoId;
	
	public PronosticoWriter(String pronosticoId) {
		this.pronosticoId = pronosticoId;
	}

	public void write(Collection<PronosticoInput>lst) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(WorldCupProperties.getInstance().getPronosticiFolder(), this.pronosticoId+".csv"));
			for(PronosticoInput p: lst) {
				fos.write(p.toString().getBytes());
				fos.write("\n".getBytes());
			}
		} catch(IOException ex) {
			System.err.println("Errore durante la scrittura del file:"+ex.getMessage());
		}finally {
			try {if(fos!=null) fos.close();} catch(IOException exx) {}
		}
	}
}
