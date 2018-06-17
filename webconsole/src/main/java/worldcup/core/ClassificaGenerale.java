package worldcup.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ClassificaGenerale {

	private List<String> categorieAutorizzate = null;
	private Gioco gioco = null;
	
	public ClassificaGenerale() {
		this.gioco = new Gioco();
		this.categorieAutorizzate = new ArrayList<>();
		this.categorieAutorizzate.add("Link");
	}

	public Map<Pronostico, Integer> getClassificaGenerale(String categoria) {
		Map<Pronostico, Integer> classifica = this.gioco.getClassifica();

		if(categoria != null && this.categorieAutorizzate.contains(categoria)) {
			// Gestire filtro per visualizzare solo gli utenti indicati
			// da utilizzare solo per vedere categorie di utenti autorizzate
			Map<Pronostico, Integer> toReturn = new HashMap<>();
			for(Pronostico pronostico: classifica.keySet()) {
				if(categoria.equals(pronostico.getPlayer().getCategoria()))
					toReturn.put(pronostico, classifica.get(pronostico));
			}
			
			LinkedHashMap<Pronostico, Integer> reverseSortedMap = new LinkedHashMap<>();
			
			//Use Comparator.reverseOrder() for reverse ordering
			toReturn.entrySet()
			    .stream()
			    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
			    .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
			
			return reverseSortedMap;


		}
		
		return classifica;
	}
	
	public List<Pronostico> getPronostici() {
		return this.gioco.getListaPronostici();
	}
}
