package worldcup.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClassificaGenerale {

	private List<String> categorieAutorizzate = null;
	private Gioco gioco = null;
	
	public ClassificaGenerale() {
		this.gioco = new Gioco();
		this.categorieAutorizzate = new ArrayList<>();
		this.categorieAutorizzate.add("link");
	}

	public Map<Pronostico, Integer> getClassificaGenerale(String categoria) {
		Map<Pronostico, Integer> classifica = this.gioco.getClassifica();

		if(categoria != null && this.categorieAutorizzate.contains(categoria)) {
			// Gestire filtro per visualizzare solo gli utenti indicati
			// da utilizzare solo per vedere categorie di utenti autorizzate
			
		}
		
		return classifica;
	}
	
	
}
