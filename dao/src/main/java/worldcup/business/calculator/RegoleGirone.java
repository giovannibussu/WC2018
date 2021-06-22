package worldcup.business.calculator;

import java.util.Collection;
import java.util.Map;

public class RegoleGirone {
	private Regole regoleVerticali;
	private Regole regoleOrizzontali;
	
	public Classifica getClassificaVerticale(Collection<GironePerformance> classificheVerticali) {
		Classifica classifica = new Classifica();

		GironePerformanceComparator gpc = new GironePerformanceComparator();
		gpc.setRegole(this.regoleVerticali);
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
	
}
