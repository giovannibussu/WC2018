package worldcup.core;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;
import java.io.FileNotFoundException;
import java.util.Random;
import java.io.IOException;
/**
 * Hello world!
 *
 */
public class Test 
{
	private static final  String[] spreadsheetId = { "player1", "player2"  };

	public static final void PronosticoWriter(String pronosticoId) {
		Random rand = new Random();
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(Costanti.PRONOSTICO_FOLDER, pronosticoId+".csv"));
			for (int i=1; i<=64; i++) {
				if (i==63) continue;
				PronosticoInput p = new PronosticoInput();
				p.setId(""+i);
				do {
					p.setHome(rand.nextInt(6));
					p.setAway(rand.nextInt(6));
				} while( i>=49 && i<= 64 && p.getHome()==p.getAway());

				fos.write(p.toString().getBytes());
				fos.write("\n".getBytes());
			}
		} catch(IOException ex) {
			System.err.println("Errore durante la scrittura del file:"+ex.getMessage());
		}finally {
			try {if(fos!=null) fos.close();} catch(IOException exx) {}
		}
	}
	

	public static void main( String[] args ) throws Exception
	{

		Torneo[] Tornei = new Torneo[spreadsheetId.length];
		PronosticoReader reader ;
		int j=0;
		for (String id : spreadsheetId) {
			PronosticoWriter(id);
                        System.out.println("Cerco versione cache per "+id);
                        reader = new FileSystemPronosticoReader(id);

			Tornei[j] = ExampleTorneoReader.getTorneo();
	                for (PronosticoInput pronostico : reader.readResults()) {
	                        System.out.println(pronostico);
        	                Tornei[j].play(pronostico.getId(),pronostico.getHome(),pronostico.getAway());
                	}
			j++;

		}

		System.out.println("Pronostici recuperati");

		//Leggo un torneo. Attualmente si dovra' leggere da file una volta per pronostico... TODO migliorare 
		PronosticoWriter("master");
		Torneo risultatoUfficiale = ExampleTorneoReader.getTorneo();
		reader = new FileSystemPronosticoReader("master");
                for (PronosticoInput pronostico : reader.readResults()) {
	                System.out.println(pronostico);
                        risultatoUfficiale.play(pronostico.getId(),pronostico.getHome(),pronostico.getAway());
                }

		j=0;	
		for (String id : spreadsheetId) {
			System.out.println("Pronostico giocatore: " +id);
			//stampa il pronostico di un girone identificato come da file gironi.txt
			System.out.println(Tornei[j].getAbstractSubTorneo("A").toString());
			System.out.println(Tornei[j].getAbstractSubTorneo("B").toString());
			System.out.println(Tornei[j].getAbstractSubTorneo("C").toString());
			System.out.println(Tornei[j].getAbstractSubTorneo("D").toString());
			System.out.println(Tornei[j].getAbstractSubTorneo("E").toString());
			System.out.println(Tornei[j].getAbstractSubTorneo("F").toString());
			System.out.println(Tornei[j].getAbstractSubTorneo("G").toString());
			System.out.println(Tornei[j].getAbstractSubTorneo("H").toString());
	
			for (int i=49; i<=64; i++) {
				if (i== 63) continue;
				System.out.println(Tornei[j].getAbstractSubTorneo(""+i).toString());
			}

			System.out.println("Pronostico vincitore: " +Tornei[j].getWinner().getNome());
			System.out.println("Punti "+id+": "+Tornei[j].getPoints(risultatoUfficiale));
			j++;
		}
		// stampa il pronostico di una partita
		

		// stampa i punti presi rispetto al pronostico ufficiale, valevoli per la classifica
		//System.out.println("Punti A:" +pronosticoA.getPoints(risultatoUfficiale));
		//System.out.println("Punti B:" +pronosticoB.getPoints(risultatoUfficiale));
	}
	
}
