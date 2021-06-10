package worldcup.impl.utils;

import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;

public class TorneoAuthorizationManager {

	@Value("${torneo.username}")
	String username;
	@Value("${torneo.password}")
	String password;
	
	public void autorizza(Logger logger, HttpServletRequest request) throws RuntimeException {
		// this.addDefaultHeader("Authorization", "Basic " + new String(Base64.getEncoder().encode((username.get()+":"+password.get()).getBytes())));
		
		String authHeader = request.getHeader("Authorization");
		
		if(authHeader != null) {
			String placeHolder = "Basic ";
			String encoded = authHeader.substring(placeHolder.length()); // elimino porzione Basic 
			byte[] decode = Base64.getDecoder().decode(encoded);
			String coppia = new String(decode);
			logger.debug("Contenuto header auth decodificato: " + coppia);
			String[] valori = coppia.split(":");
			
			String usernameInput = valori[0];
			String passwordInput = valori[1];
			boolean autorizzato = this.username.equals(usernameInput) && this.password.equals(passwordInput);
			
			if(autorizzato) {
				return;
			}
		}
		
		throw new RuntimeException("Utente non autorizzato");
	}
}
