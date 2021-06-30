package worldcup.clients.impl;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import worldcup.clients.api.TorneoApi;
import worldcup.clients.model.Partita;
import worldcup.clients.model.Pronostico;
import worldcup.clients.model.TipoDistribuzione;

public class Main extends ApiClient {

	public static void main(String[] args) {
		ApiClient client = new PatchedApiClient(Optional.empty(), Optional.empty());
		client.setBasePath("http://127.0.0.1:8081/api-worldcup/api/v1");

		TorneoApi torneoApi = new TorneoApi(client);
//		ApplicativiApi applicativiApi = new ApplicativiApi(client);
//		ErogazioniApi erogazioniApi = new ErogazioniApi(client);
//		ErogazioniConfigurazioneApi erogazioniConfigurazioneApi = new ErogazioniConfigurazioneApi(client);
//
		try {
			
//			List<Pronostico> classifica = torneoApi.getClassifica("EURO2021", null);
//			
//			for(Pronostico pronostico: classifica) {
//				System.out.println(pronostico.getGiocatore().getNome() + ": " + pronostico.getPunti());
//			}
			
		
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

//			Pronostico uff = torneoApi.getPronosticoUfficiale("EURO2021");
//			
//			for(Partita p: uff.getPartite()) {
//				System.out.println(p.getIdPartita());
//			}
//			List<Partita> lst = torneoApi.listPartite("EURO2021", null, null, formatter.format(new Date()), null, true, OrderType.ASC);
			
//			List<Pronostico> clas = torneoApi.getClassifica("EURO2021", null);
			
////			for(Pronostico p: clas) {
//				try {
//			torneoApi.getPronosticiPartita("EURO2021", "40");
//					Pronostico pr = torneoApi.getPronostico("EURO2021", "zulio");// p.getGiocatore().getIdGiocatore());
			
			torneoApi.getDistribuzionePartita("EURO2021", "40", TipoDistribuzione.RISULTATO);
					Pronostico pr = torneoApi.getPronosticoUfficiale("EURO2021");// p.getGiocatore().getIdGiocatore());
//					for(Partita p1: pr.getPartite()) {
					pr.getPartite().stream()
					.filter(p -> Integer.parseInt(p.getIdPartita()) >= 37 && Integer.parseInt(p.getIdPartita()) <= 44)
					.sorted(new Comparator<Partita>() {

						@Override
						public int compare(Partita o1, Partita o2) {
							return Integer.parseInt(o1.getIdPartita()) - Integer.parseInt(o2.getIdPartita());
						}}).forEach(p2 -> {
						System.out.println(p2.getIdPartita() + "-" + p2.getCasa().getNome() + " - " + p2.getTrasferta().getNome() + " - " +p2.getRisultato().getGoalCasa() + " - " +p2.getRisultato().getGoalTrasferta() );
					});
//					}
//				} catch(Exception e) {
////					System.err.println("Pronostico "+p.getGiocatore().getIdGiocatore()+" errato");
//				}
//			}
////			for(Partita partita: lst) {
////				System.out.println(partita.getIdPartita() + " " + partita.getData());
////			}
			
		} catch(Exception e) {
			e.printStackTrace(System.err);
			System.err.println(e.getMessage());
		}

	}

}
