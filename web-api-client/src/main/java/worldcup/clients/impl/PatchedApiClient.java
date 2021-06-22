package worldcup.clients.impl;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import worldcup.clients.api.TorneoApi;
import worldcup.clients.model.OrderType;
import worldcup.clients.model.Partita;
import worldcup.clients.model.Pronostico;

public class PatchedApiClient extends ApiClient {

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

//			List<Partita> lst = torneoApi.listPartite("EURO2021", null, null, formatter.format(new Date()), null, true, OrderType.ASC);
			
			List<Pronostico> clas = torneoApi.getClassifica("EURO2021", null);
			
//			for(Pronostico p: clas) {
//				try {
//					torneoApi.getPronostico("EURO2021", p.getGiocatore().getIdGiocatore());
//				} catch(Exception e) {
//					System.err.println("Pronostico "+p.getGiocatore().getIdGiocatore()+" errato");
//				}
//			}
//			for(Partita partita: lst) {
//				System.out.println(partita.getIdPartita() + " " + partita.getData());
//			}
			
		} catch(Exception e) {
			e.printStackTrace(System.err);
			System.err.println(e.getMessage());
		}

	}

	public PatchedApiClient(Optional<String> username, Optional<String> password) {
		
		if (username.isPresent() && password.isPresent())
		{
			this.addDefaultHeader("Authorization", "Basic " + new String(Base64.getEncoder().encode((username.get()+":"+password.get()).getBytes())));
		}
		
		ExtendedJSON json = new ExtendedJSON();
//		json.setDateTimeFormat(java.time.format.DateTimeFormatter.ofPattern(""));
		this.setJSON(json);
		
	}

//    public RequestBody serialize(Object obj, String contentType) throws ApiException {
//    	
//    	if(obj instanceof String) {
//    		return RequestBody.create(MediaType.parse(contentType), (String) obj);
//    	} else {
//    		return super.serialize(obj, contentType);
//    	}
//    }
}
