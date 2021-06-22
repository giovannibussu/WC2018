package worldcup.business.calculator;

import java.util.Collection;
import java.util.Map;

public class RegoleGirone {
	private Regole regoleVerticali;
	private Regole regoleClassificaAvulsa;
	private Regole regoleOrizzontali;
	
	public Classifica getClassificaVerticale(Collection<GironePerformance> classificheVerticali) {
		Classifica classifica = new Classifica();

		GironePerformanceComparator gpc = new GironePerformanceComparator();
		gpc.setRegole(this.regoleVerticali);
		gpc.setRegoleClassificaAvulsa(this.regoleClassificaAvulsa);
		Map<Integer, GironePerformance> squadre = gpc.sort(classificheVerticali);

		classifica.setSquadre(squadre);
		return classifica;
	}

	public Classifica getClassificaOrizzontale(Collection<GironePerformance> classificheVerticali) {
		Classifica classifica = new Classifica();
		
		GironePerformanceComparator gpc = new GironePerformanceComparator();
		gpc.setRegole(this.regoleOrizzontali);
		Map<Integer, GironePerformance> squadre = gpc.sort(classificheVerticali);

		classifica.setSquadre(squadre);
		return classifica;
	}

	public Regole getRegoleVerticali() {
		return regoleVerticali;
	}

	public void setRegoleVerticali(Regole regoleVerticali) {
		this.regoleVerticali = regoleVerticali;
	}

	public Regole getRegoleOrizzontali() {
		return regoleOrizzontali;
	}

	public void setRegoleOrizzontali(Regole regoleOrizzontali) {
		this.regoleOrizzontali = regoleOrizzontali;
	}

	public Regole getRegoleClassificaAvulsa() {
		return regoleClassificaAvulsa;
	}

	public void setRegoleClassificaAvulsa(Regole regoleClassificaAvulsa) {
		this.regoleClassificaAvulsa = regoleClassificaAvulsa;
	}
	
}
