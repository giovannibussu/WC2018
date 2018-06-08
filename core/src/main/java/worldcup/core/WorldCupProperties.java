package worldcup.core;

import java.io.IOException;
import java.util.Properties;

public class WorldCupProperties {

	private static WorldCupProperties instance;
	public static WorldCupProperties getInstance() {
		if(instance == null)
			instance= new WorldCupProperties();
		return instance;
	}

	private String pronosticiFolder;
	private String idPronosticoUfficiale;
	
	private static final String PATH = "/worldCup.properties";
	
	public WorldCupProperties() {
		
		try {
			Properties props = new Properties();
			props.load(WorldCupProperties.class.getResourceAsStream(PATH));
			this.pronosticiFolder = props.getProperty("pronostici.folder").trim();
			this.idPronosticoUfficiale = props.getProperty("pronostici.ufficiale.id").trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public String getIdPronosticoUfficiale() {
		return idPronosticoUfficiale;
	}

	public String getPronosticiFolder() {
		return pronosticiFolder;
	}
}
