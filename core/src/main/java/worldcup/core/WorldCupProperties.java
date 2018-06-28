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
	private boolean mostraPuntiPassaggioGironi;
	public boolean isMostraPuntiPassaggioGironi() {
		return mostraPuntiPassaggioGironi;
	}


	public boolean isMostraPuntiPassaggioOttavi() {
		return mostraPuntiPassaggioOttavi;
	}


	public boolean isMostraPuntiPassaggioQuarti() {
		return mostraPuntiPassaggioQuarti;
	}


	public boolean isMostraPuntiPassaggioSemifinali() {
		return mostraPuntiPassaggioSemifinali;
	}


	public boolean isMostraPuntiPassaggioFinale() {
		return mostraPuntiPassaggioFinale;
	}


	private boolean mostraPuntiPassaggioOttavi;
	private boolean mostraPuntiPassaggioQuarti;
	private boolean mostraPuntiPassaggioSemifinali;
	private boolean mostraPuntiPassaggioFinale;
	
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
			
			this.mostraPuntiPassaggioGironi = Boolean.parseBoolean(externalProps.getProperty("mostraPuntiPassaggio.gironi", "false").trim());
			this.mostraPuntiPassaggioOttavi = Boolean.parseBoolean(externalProps.getProperty("mostraPuntiPassaggio.ottavi", "false").trim());
			this.mostraPuntiPassaggioQuarti = Boolean.parseBoolean(externalProps.getProperty("mostraPuntiPassaggio.quarti", "false").trim());
			this.mostraPuntiPassaggioSemifinali = Boolean.parseBoolean(externalProps.getProperty("mostraPuntiPassaggio.semifinali", "false").trim());
			this.mostraPuntiPassaggioFinale = Boolean.parseBoolean(externalProps.getProperty("mostraPuntiPassaggio.finale", "false").trim());
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
