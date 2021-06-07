package worldcup.orm;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class JavaxPersistenceSqlGenerator {

	public enum TipiDatabase {POSTGRESQL,MYSQL,ORACLE, DERBY}
	
	public static void generate(String persistenceId, String folder) throws IOException {
		
		TipiDatabase[] values = {TipiDatabase.POSTGRESQL,TipiDatabase.MYSQL,TipiDatabase.ORACLE, TipiDatabase.DERBY};
		for(TipiDatabase tipoDatabase: values) {
			generate(persistenceId, folder, tipoDatabase);
		}
	}
	
	private static void generate(String persistenceId, String folder, TipiDatabase tipoDatabase) throws IOException {

		String create= folder + tipoDatabase.toString().toLowerCase() +  "/"+persistenceId+".sql";
		String drop = folder + tipoDatabase.toString().toLowerCase()+ "/"+persistenceId+"_drop.sql";

		Files.deleteIfExists(Paths.get(create));
        Files.deleteIfExists(Paths.get(drop));

        Map<String, String> map = getMap(persistenceId, create, drop, tipoDatabase);
		
		// Persistence.generateSchema(persistenceId, map);
		try {
			Class<?> cPersistence = Class.forName("javax.persistence.Persistence");
			Method m = cPersistence.getMethod("generateSchema", persistenceId.getClass(), java.util.Map.class);
			m.invoke(null, persistenceId, map);
		}catch(Throwable t) {
			throw new IOException(t);
		}
      
	}

	private static Map<String, String> getMap(String persistenceId, String create, String drop, TipiDatabase tipoDatabase) throws IOException {
		Map<String, String> map = new HashMap<String, String>();

        map.put("javax.persistence.schema-generation.scripts.action", "drop-and-create");
        map.put("javax.persistence.schema-generation.scripts.create-target", create);
        map.put("javax.persistence.schema-generation.scripts.drop-target", drop);
        map.put("hibernate.hbm2ddl.delimiter", ";");
        map.put("hibernate.format_sql", "true");
		
        switch(tipoDatabase) {
		case DERBY:
	        map.put("javax.persistence.database-product-name", "Derby");
	        map.put("hibernate.dialect","org.hibernate.dialect.DerbyTenSevenDialect");
			break;
		case MYSQL:
	        map.put("javax.persistence.database-product-name", "Mysql");
	        map.put("hibernate.dialect","org.hibernate.dialect.MySQL5InnoDBDialect");
			break;
		case ORACLE:
	        map.put("javax.persistence.database-product-name", "Oracle");
	        map.put("hibernate.dialect","org.hibernate.dialect.Oracle10gDialect");
			break;
		case POSTGRESQL:
	        map.put("javax.persistence.database-product-name", "Postgresql");
	        map.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
	        map.put("javax.persistence.database-major-version", "9");
	        map.put("javax.persistence.database-minor-version", "1");
			break;
		default:
			break;
		}

		return map;
	}

}
