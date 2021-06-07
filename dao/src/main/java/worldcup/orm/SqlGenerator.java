package worldcup.orm;

import java.io.IOException;

public class SqlGenerator {

	public static void main(String[] args) {
		String folder = "../src/main/resources/database/sql/";
		try {
			JavaxPersistenceSqlGenerator.generate("pronostici", folder);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
	}
}
