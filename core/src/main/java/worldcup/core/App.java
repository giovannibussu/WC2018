package worldcup.core;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void main( String[] args ) throws Exception
	{
		//Leggo un torneo. Attualmente si dovra' leggere da file una volta per pronostico... TODO migliorare 
		Torneo pronosticoA = ExampleTorneoReader.getTorneo();
		Torneo pronosticoB = ExampleTorneoReader.getTorneo();
		Torneo risultatoUfficiale = ExampleTorneoReader.getTorneo();
		
		//metodo play, inserisce il pronostico (o il risultato ufficiale) per una partita. Per gli ID delle partite vedere l'excel o i file gironi.txt e knockout.txt 
		pronosticoA.play("1", 1, 0);
		pronosticoB.play("1", 2, 0);
		risultatoUfficiale.play("1", 2, 0);
		//stampa tutto il pronostico (verboso)
//		System.out.println(pronostico.toString());

		//stampa il pronostico di un girone identificato come da file gironi.txt
		System.out.println(pronosticoA.getAbstractSubTorneo("A").toString());
		
		// stampa il pronostico di una partita
		System.out.println("Pronostico partita 1: " +pronosticoA.getMatch("1").toString());
		

		// stampa i punti presi rispetto al pronostico ufficiale, valevoli per la classifica
		System.out.println("Punti A:" +risultatoUfficiale.getPoints(pronosticoA));
		System.out.println("Punti B:" +risultatoUfficiale.getPoints(pronosticoB));
	}
	
}
