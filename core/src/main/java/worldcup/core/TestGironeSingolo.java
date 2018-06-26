package worldcup.core;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Random;
/**
 * Hello world!
 *
 */
public class TestGironeSingolo 
{
	private static final  String[] spreadsheetId = { "1vt3gNM-gCkmP6mPXY3w6iMNEWdUeLztNUxtocjwLefA" , "17wewBdl3MYFb3WlLjBxYci02Z4UH6oYXnvyohSgTKV4", "1obTk64_qkugcWR0UDF0D09lQ2CwoAXzpUKf4q4XppxQ", "1KSUAPd-gsCCxs1RVT7MNn2U173sE4dzf48H9Pd87qw0", "aqS9Ibo8TcFCxeo3szzY9_18ABr3Vjxj4b_VFcfFNFw"};

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
	

	public static final void PronosticoWriter(String pronosticoId,int limite) {
		Random rand = new Random();
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(WorldCupProperties.getInstance().getPronosticiFolder(), pronosticoId+".csv"));
			for (int i=1; i<=64; i++) {
				if (i==63) continue;
				if (i> limite) break;
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
			
			Collection<PronosticoInput> readResults = null;
			//PronosticoWriter(id);
            System.out.println("Cerco versione cache per "+id);
			try {
				reader = new FileSystemPronosticoReader(id);
				readResults = reader.readResults().values();
			} catch(FileNotFoundException e) {
				reader = new GoogleApiPronosticoReader(id);
				readResults = reader.readResults().values();
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(new File(WorldCupProperties.getInstance().getPronosticiFolder(), id+".csv"));
					for(PronosticoInput p: readResults) {
						fos.write(p.toString().getBytes());
						fos.write("\n".getBytes());
					}
				} catch(IOException ex) {
					System.err.println("Errore durante la scrittura del file:"+ex.getMessage());
				}finally {
					try {if(fos!=null) fos.close();} catch(IOException exx) {}
				}
				reader = new FileSystemPronosticoReader(id);
				readResults = reader.readResults().values();
			}
			
			
			

                        

			Tornei[j] = ExampleTorneoReader.getTorneo(id);
	                for (PronosticoInput pronostico : readResults) {
	                        System.out.println(pronostico);
        	                Tornei[j].play(pronostico.getId(),pronostico.getHome(),pronostico.getAway());
                	}
			j++;

		}

		System.out.println("Pronostici recuperati");

		//Leggo un torneo. Attualmente si dovra' leggere da file una volta per pronostico... TODO migliorare 
		PronosticoWriter("master",49);
		Torneo risultatoUfficiale = ExampleTorneoReader.getTorneo("master");
		reader = new FileSystemPronosticoReader("master");
                for (PronosticoInput pronostico : reader.readResults().values()) {
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

			System.out.println("Pronostico vincitore: " +((Tornei[j].getWinner() != null) ? Tornei[j].getWinner().getNome() : "Non indicato"));
			//System.out.println("Punti "+id+": "+Tornei[j].getPoints(risultatoUfficiale));
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

                        System.out.println("Pronostico vincitore: " +((risultatoUfficiale.getWinner() != null ) ? risultatoUfficiale.getWinner().getNome() : "Non indicato"));

	}
	
}
