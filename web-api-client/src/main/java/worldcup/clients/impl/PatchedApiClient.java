package worldcup.clients.impl;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import worldcup.clients.api.TorneoApi;
import worldcup.clients.impl.ApiClient;
import worldcup.clients.model.Pronostico;

public class PatchedApiClient extends ApiClient {

	public static void main(String[] args) {
		ApiClient client = new PatchedApiClient(Optional.empty(), Optional.empty());
		client.setBasePath("http://127.0.0.1:8080/api-worldcup/api/v1");

		TorneoApi torneoApi = new TorneoApi(client);
//		ApplicativiApi applicativiApi = new ApplicativiApi(client);
//		ErogazioniApi erogazioniApi = new ErogazioniApi(client);
//		ErogazioniConfigurazioneApi erogazioniConfigurazioneApi = new ErogazioniConfigurazioneApi(client);
//
		try {
			
			List<Pronostico> classifica = torneoApi.getClassifica("idtorneo", null);
			
			for(Pronostico pronostico: classifica) {
				System.out.println(pronostico.getGiocatore().getNome());
			}
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
		
		this.setJSON(new ExtendedJSON());
		
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
