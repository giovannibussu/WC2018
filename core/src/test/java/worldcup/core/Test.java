package worldcup.core;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import worldcup.core.utils.ExampleTorneoReader;
import worldcup.core.utils.FileSystemPronosticoReader;
/**
 * Hello world!
 *
 */
public class Test 
{
	private static final int N_PLAYERS = 10;
	
	public static final void PronosticoWriter(String pronosticoId) {
		Random rand = new Random();
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(WorldCupProperties.getInstance().getPronosticiFolder(), pronosticoId+".csv"));
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

		Torneo[] Tornei = new Torneo[N_PLAYERS];
		PronosticoReader reader ;
		int j=0;
		for (int nPlayer =0; nPlayer<N_PLAYERS;nPlayer++) {
			String id = "player"+nPlayer;
			PronosticoWriter(id);
                        System.out.println("Cerco versione cache per "+id);
                        reader = new FileSystemPronosticoReader(id);

			Tornei[j] = ExampleTorneoReader.getTorneo(id);
	                for (PronosticoInput pronostico : reader.readResults().values()) {
	                        System.out.println(pronostico);
        	                Tornei[j].play(pronostico.getId(),pronostico.getHome(),pronostico.getAway());
                	}
			j++;

		}

		System.out.println("Pronostici recuperati");

		//Leggo un torneo. Attualmente si dovra' leggere da file una volta per pronostico... TODO migliorare 
		PronosticoWriter(WorldCupProperties.getInstance().getIdPronosticoUfficiale());
		Torneo risultatoUfficiale = ExampleTorneoReader.getTorneo(WorldCupProperties.getInstance().getIdPronosticoUfficiale());
		reader = new FileSystemPronosticoReader(WorldCupProperties.getInstance().getIdPronosticoUfficiale());
                for (PronosticoInput pronostico : reader.readResults().values()) {
	                System.out.println(pronostico);
                        risultatoUfficiale.play(pronostico.getId(),pronostico.getHome(),pronostico.getAway());
                }

		j=0;	
		for (int nPlayer =0; nPlayer<N_PLAYERS;nPlayer++) {
			String id = "player"+nPlayer;
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
		


                        System.out.println("Risulati ufficiali");
                        //stampa il pronostico di un girone identificato come da file gironi.txt
                        System.out.println(risultatoUfficiale.getAbstractSubTorneo("A").toString());
                        System.out.println(risultatoUfficiale.getAbstractSubTorneo("B").toString());
                        System.out.println(risultatoUfficiale.getAbstractSubTorneo("C").toString());
                        System.out.println(risultatoUfficiale.getAbstractSubTorneo("D").toString());
                        System.out.println(risultatoUfficiale.getAbstractSubTorneo("E").toString());
                        System.out.println(risultatoUfficiale.getAbstractSubTorneo("F").toString());
                        System.out.println(risultatoUfficiale.getAbstractSubTorneo("G").toString());
                        System.out.println(risultatoUfficiale.getAbstractSubTorneo("H").toString());

                        for (int i=49; i<=64; i++) {
                                if (i== 63) continue;
                                System.out.println(risultatoUfficiale.getAbstractSubTorneo(""+i).toString());
                        }

                        System.out.println("Pronostico vincitore: " +risultatoUfficiale.getWinner().getNome());

	}
	
}
