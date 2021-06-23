package worldcup.clients.impl;

import java.util.Base64;
import java.util.Optional;

public class PatchedApiClient extends ApiClient {

	public PatchedApiClient(Optional<String> username, Optional<String> password) {
		
		if (username.isPresent() && password.isPresent())
		{
			this.addDefaultHeader("Authorization", "Basic " + new String(Base64.getEncoder().encode((username.get()+":"+password.get()).getBytes())));
		}
		
		ExtendedJSON json = new ExtendedJSON();
		this.setJSON(json);
		
	}
}
