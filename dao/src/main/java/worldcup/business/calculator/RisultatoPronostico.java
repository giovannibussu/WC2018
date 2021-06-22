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
	
	private static void add(Map<SubdivisionVO, Integer> map, SubdivisionVO subdivisionVO) {
		int res = map.containsKey(subdivisionVO) ? map.get(subdivisionVO): 0;
		map.put(subdivisionVO, res + 1);
	}

	public void addRisultati(SubdivisionVO subdivisionVO) {
		add(this.risultati, subdivisionVO);
	}
	public void addRisultatiEsatti(SubdivisionVO subdivisionVO) {
		add(this.risultatiEsatti, subdivisionVO);
	}
	public void addPassaggi(SubdivisionVO subdivisionVO) {
		add(this.passaggi, subdivisionVO);
	}
	public void addPosizioni(SubdivisionVO subdivisionVO) {
		add(this.posizioni, subdivisionVO);
	}
}
