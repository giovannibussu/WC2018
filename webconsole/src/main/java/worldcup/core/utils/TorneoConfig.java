package worldcup.core.utils;

import java.io.InputStream;
import java.util.Properties;

public class TorneoConfig {

	private static TorneoConfig instance= null;

	private Properties props = null;

	public static final String PROPERTIES_FILE_NAME = "torneo.properties";
	public static final String PROPERTIES_FILE = "/" + PROPERTIES_FILE_NAME;

	public static final String ID_TORNEO_DEFAULT = "EURO2021";
	public static final String API_BASE_URL = "http://127.0.0.1:8080/api-worldcup/api/v1";

	private String idTorneo = ID_TORNEO_DEFAULT;
	private String apiBaseUrl = API_BASE_URL;

	public static TorneoConfig getInstance() throws Exception {
		if(instance == null)
			init();

		return instance;
	}

	private static synchronized void init() throws Exception {
		if(instance == null)
			instance = new TorneoConfig();
	}

	public TorneoConfig() throws Exception{
		InputStream is = TorneoConfig.class.getResourceAsStream(PROPERTIES_FILE);
		this.props = new Properties();
		this.props.load(is);

		this.idTorneo = getProperty("idTorneo", props, true);
		this.apiBaseUrl = getProperty("urlApi", props, true);

	}

	private static String getProperty(String name, Properties props, boolean required) throws Exception {
		String value = props.getProperty(name);
		if(value == null) {
			if(required)
				throw new Exception("Property ["+name+"] non trovata");
			else return null;
		}

		return value.trim();
	}


	public String getIdTorneo() {
		return idTorneo;
	}

	public String getApiBaseUrl() {
		return apiBaseUrl;
	}
}
