package worldcup.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class WorldCupProperties {

	private static WorldCupProperties instance;
	public static WorldCupProperties getInstance() {
		if(instance == null)
			instance= new WorldCupProperties();
		return instance;
	}

	
	private String worldCupExternalFolder;
	private String pronosticiFolder;
	private String idPronosticoUfficiale;

	private String username;
	private String password;
	private static final String PATH = "worldCup.properties";
	
	public WorldCupProperties() {
		
		try {
			Properties internalProps = new Properties();
			internalProps.load(WorldCupProperties.class.getResourceAsStream("/"+PATH));
			this.worldCupExternalFolder = internalProps.getProperty("worldcup.externalFolder").trim();

			Properties externalProps = new Properties();
			externalProps.load(new FileInputStream(new File(this.worldCupExternalFolder, PATH)));
			this.pronosticiFolder = externalProps.getProperty("pronostici.folder").trim();
			this.idPronosticoUfficiale = externalProps.getProperty("pronostici.ufficiale.id").trim();
			this.username = externalProps.getProperty("username").trim();
			this.password = externalProps.getProperty("password").trim();
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

	public String getWorldCupExternalFolder() {
		return worldCupExternalFolder;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}