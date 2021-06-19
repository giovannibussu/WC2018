package worldcup.business.calculator;

import java.util.HashMap;
import java.util.Map;

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
	
}
