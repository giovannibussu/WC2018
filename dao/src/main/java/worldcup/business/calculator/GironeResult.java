package worldcup.business.calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class GironeResult {

	private Map<String, Classifica> classificheVerticali;
	private Map<Integer, Classifica> classificheOrizzontali;

	public GironeResult() {
		this.classificheVerticali = new HashMap<>();
		this.classificheOrizzontali = new HashMap<>();
	}
	
	public void setClassificaVerticale(String codiceGirone, Classifica classifica) {
		this.classificheVerticali.put(codiceGirone, classifica);
	}
	
	public void setClassificaOrizzontale(Integer posizione, Classifica classifica) {
		this.classificheOrizzontali.put(posizione, classifica);
	}
	
	public Classifica getClassificaVerticale(String codiceGirone) {
		return this.classificheVerticali.get(codiceGirone);
	}
	
	public Classifica getClassificaOrizzontale(int posizione) {
		return this.classificheOrizzontali.get(posizione);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		for(Entry<String, Classifica> e: classificheVerticali.entrySet()) {
			sb.append("Girone " + e.getKey() + "\n");
			e.getValue().getSquadre().keySet().stream().sorted().forEach(k -> {
				sb.append("["+k+"] " + e.getValue().getSquadre().get(k).getSquadra().getNome() + "\n");
			});
		}
		
		sb.append("Migliori terze\n");
		classificheOrizzontali.get(3).getSquadre().keySet().stream().sorted().forEach(k -> {
			sb.append("["+k+"] " + classificheOrizzontali.get(3).getSquadre().get(k).getSquadra().getNome() + "\n");
		});

		return sb.toString();
	}
}
