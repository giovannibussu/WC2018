package worldcup.clients.impl;

import java.net.Authenticator;
import java.util.List;
import java.util.Optional;

import worldcup.clients.api.TorneoApi;
import worldcup.clients.model.Partita;

public class PatchedApiClient extends ApiClient {

	public static void main(String[] args) {
		ApiClient client = new PatchedApiClient(Optional.empty(), Optional.empty());
		client.setHost("127.0.0.1");
		client.setPort(8081);
		client.setBasePath("/api-worldcup/api/v1");

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
			
		
			List<Partita> lst = torneoApi.listPartite("EURO2021", null, null, null, null);
			
			for(Partita partita: lst) {
				System.out.println(partita.getIdPartita() + " " + partita.getData());
			}
			
		} catch(Exception e) {
			e.printStackTrace(System.err);
			System.err.println(e.getMessage());
		}

	}

	public PatchedApiClient(Optional<String> username, Optional<String> password) {
		
		super();
//		if (username.isPresent() && password.isPresent())
//		{
////			this.addDefaultHeader("Authorization", "Basic " + new String(Base64.getEncoder().encode((username.get()+":"+password.get()).getBytes())));
//		}
//		
//		ExtendedJSON json = new ExtendedJSON();
////		json.setDateTimeFormat(java.time.format.DateTimeFormatter.ofPattern(""));
//		this.setJSON(json);
		
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
