package worldcup.business.calculator;

import java.util.HashMap;
import java.util.Map;

import worldcup.orm.vo.SubdivisionVO;

public class RisultatoPronostico {

	
	private Map<SubdivisionVO, Integer> risultatiEsatti = new HashMap<>();
	private Map<SubdivisionVO, Integer> risultati = new HashMap<>();
	private Map<SubdivisionVO, Integer> posizioni = new HashMap<>();
	private Map<SubdivisionVO, Integer> passaggi = new HashMap<>();
	
	public Map<SubdivisionVO, Integer> getRisultatiEsatti() {
		return this.risultatiEsatti;
	}
	public Map<SubdivisionVO, Integer> getRisultati() {
		return this.risultati;
	}
	public Map<SubdivisionVO, Integer> getPassaggi() {
		return this.passaggi;
	}
	public Map<SubdivisionVO, Integer> getPosizioni() {
		return this.posizioni;
	}
	public void addRisultati(SubdivisionVO subdivisionVO) {
		this.risultati.put(subdivisionVO, this.risultati.get(subdivisionVO) + 1);
	}
	public void addRisultatiEsatti(SubdivisionVO subdivisionVO) {
		this.risultatiEsatti.put(subdivisionVO, this.risultatiEsatti.get(subdivisionVO) + 1);
	}
	public void addPassaggi(SubdivisionVO subdivisionVO) {
		this.passaggi.put(subdivisionVO, this.passaggi.get(subdivisionVO) + 1);
	}
	public void addPosizioni(SubdivisionVO subdivisionVO) {
		this.posizioni.put(subdivisionVO, this.posizioni.get(subdivisionVO) + 1);
	}
}
