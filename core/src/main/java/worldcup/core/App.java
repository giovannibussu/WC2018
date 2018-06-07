package worldcup.core;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;
import java.io.FileNotFoundException;
/**
 * Hello world!
 *
 */
public class App 
{
	private static final  String spreadsheetId = "1QHIq-ZmhoC3mSlBlAmXjhndZb7Ea33SIcZ3W4olHH2I";
	public static void main( String[] args ) throws Exception
	{
		PronosticoReader reader ;
		try { 
	 		reader = new FileSystemPronosticoReader(spreadsheetId);
		} catch (FileNotFoundException fnfe) {
			reader = new GoogleApiPronosticoReader(spreadsheetId);
                        File file = new File(Costanti.PRONOSTICO_FOLDER, spreadsheetId + ".csv");
                        FileOutputStream fos = new FileOutputStream(file);
 
                        OutputStreamWriter osw = new OutputStreamWriter(fos);
 
                        for (PronosticoInput pronostico : reader.readResults()) {
                  	      System.out.println(pronostico);
                        	osw.write(pronostico.getId()+","+pronostico.getHome()+","+pronostico.getAway());
                        }

 
			osw.close();
		}

		//Leggo un torneo. Attualmente si dovra' leggere da file una volta per pronostico... TODO migliorare 
		Torneo pronosticoA = ExampleTorneoReader.getTorneo();
		Torneo pronosticoB = ExampleTorneoReader.getTorneo();
		Torneo risultatoUfficiale = ExampleTorneoReader.getTorneo();
		
		//metodo play, inserisce il pronostico (o il risultato ufficiale) per una partita. Per gli ID delle partite vedere l'excel o i file gironi.txt e knockout.txt 
		for (PronosticoInput pronostico : reader.readResults()) {
			System.out.println(pronostico);
			pronosticoA.play(pronostico.getId(),pronostico.getHome(),pronostico.getAway());
		}
//pronosticoA.play("1", 1, 0);
//pronosticoB.play("1", 2, 0);
		risultatoUfficiale.play("1", 2, 0);
		//stampa tutto il pronostico (verboso)
//		System.out.println(pronosticoA.toString());

		//stampa il pronostico di un girone identificato come da file gironi.txt
		System.out.println(pronosticoA.getAbstractSubTorneo("A").toString());
		System.out.println(pronosticoA.getAbstractSubTorneo("B").toString());
		System.out.println(pronosticoA.getAbstractSubTorneo("C").toString());
		System.out.println(pronosticoA.getAbstractSubTorneo("D").toString());
		System.out.println(pronosticoA.getAbstractSubTorneo("E").toString());
		System.out.println(pronosticoA.getAbstractSubTorneo("F").toString());
		System.out.println(pronosticoA.getAbstractSubTorneo("G").toString());
		System.out.println(pronosticoA.getAbstractSubTorneo("H").toString());
		
		// stampa il pronostico di una partita
		System.out.println("Pronostico vincitore: " +pronosticoA.getWinner().getNome());
		

		// stampa i punti presi rispetto al pronostico ufficiale, valevoli per la classifica
		//System.out.println("Punti A:" +pronosticoA.getPoints(risultatoUfficiale));
		//System.out.println("Punti B:" +pronosticoB.getPoints(risultatoUfficiale));
	}
	
}
