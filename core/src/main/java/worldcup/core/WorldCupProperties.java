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
//			this.worldCupExternalFolder = internalProps.getProperty("worldcup.externalFolder").trim();

			this.pronosticiFolder = internalProps.getProperty("pronostici.folder").trim();
			this.idPronosticoUfficiale = internalProps.getProperty("pronostici.ufficiale.id").trim();
			this.username = internalProps.getProperty("username").trim();
			this.password = internalProps.getProperty("password").trim();
			
			this.mostraPuntiPassaggioGironi = Boolean.parseBoolean(internalProps.getProperty("mostraPuntiPassaggio.gironi", "false").trim());
			this.mostraPuntiPassaggioOttavi = Boolean.parseBoolean(internalProps.getProperty("mostraPuntiPassaggio.ottavi", "false").trim());
			this.mostraPuntiPassaggioQuarti = Boolean.parseBoolean(internalProps.getProperty("mostraPuntiPassaggio.quarti", "false").trim());
			this.mostraPuntiPassaggioSemifinali = Boolean.parseBoolean(internalProps.getProperty("mostraPuntiPassaggio.semifinali", "false").trim());
			this.mostraPuntiPassaggioFinale = Boolean.parseBoolean(internalProps.getProperty("mostraPuntiPassaggio.finale", "false").trim());
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

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
