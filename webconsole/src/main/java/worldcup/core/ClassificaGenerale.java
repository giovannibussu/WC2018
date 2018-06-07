package worldcup.core;

import java.util.Map;

public class ClassificaGenerale {

	private Gioco gioco = null;
	
	public ClassificaGenerale() {
		this.gioco = new Gioco();
	}

	public Map<Pronostico, Integer> getClassificaGenerale() {
		Map<Pronostico, Integer> classifica = this.gioco.getClassifica();
		return classifica;
	}
	
	
}
