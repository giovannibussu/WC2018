package worldcup.orm.vo;

import java.util.HashSet;
import java.util.Set;

public class PronosticoVO {

	private Long id;
	private String idPronostico;
	private String link;
	private GiocatoreVO giocatore;
	private TorneoVO torneo;
	private SquadraVO vincente;
	private Set<DatiPartitaVO> datiPartite = new HashSet<>();

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIdPronostico() {
		return idPronostico;
	}
	public void setIdPronostico(String idPronostico) {
		this.idPronostico = idPronostico;
	}
	public GiocatoreVO getGiocatore() {
		return giocatore;
	}
	public void setGiocatore(GiocatoreVO giocatore) {
		this.giocatore = giocatore;
	}
	public TorneoVO getTorneo() {
		return torneo;
	}
	public void setTorneo(TorneoVO torneo) {
		this.torneo = torneo;
	}
	public Set<DatiPartitaVO> getDatiPartite() {
		return datiPartite;
	}
	public void setDatiPartite(Set<DatiPartitaVO> datiPartite) {
		this.datiPartite = datiPartite;
	}
	public SquadraVO getVincente() {
		return vincente;
	}
	public void setVincente(SquadraVO vincente) {
		this.vincente = vincente;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
}
