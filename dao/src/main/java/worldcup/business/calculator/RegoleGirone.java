package worldcup.business.calculator;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RegoleGirone {
	private Regole regoleVerticali;
	private Regole regoleClassificaAvulsa;
	private Regole regoleOrizzontali;
	
	public Classifica getClassificaVerticale(Collection<GironePerformance> performance) {
		Classifica classifica = new Classifica();
		
		GironePerformanceComparator gpc = new GironePerformanceComparator();
		gpc.setRegole(this.regoleVerticali);
		List<GironePerformance> c = performance.stream().sorted(gpc).collect(Collectors.toList());
		
		Map<Integer, GironePerformance> squadre = new HashMap<Integer, GironePerformance>();
		for(int i = 0; i < c.size(); i++) {
			squadre.put(i+1, c.get(i));
		}
		
		classifica.setSquadre(squadre);
		return classifica;
	}

	public Classifica getClassificaOrizzontale(Collection<GironePerformance> classificheVerticali) {
		Classifica classifica = new Classifica();
		
		GironePerformanceComparator gpc = new GironePerformanceComparator();
		gpc.setRegole(this.regoleOrizzontali);
		List<GironePerformance> c = classificheVerticali.stream().sorted(gpc).collect(Collectors.toList());
		
		Map<Integer, GironePerformance> squadre = new HashMap<Integer, GironePerformance>();
		for(int i = 0; i < c.size(); i++) {
			squadre.put(i+1, c.get(i));
		}
		
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
