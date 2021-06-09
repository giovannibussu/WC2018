package worldcup.orm.vo;

import java.util.Date;

public class PartitaVO {

	private Long id;

	private Date data;
	private StadioVO stadio;	
	private String codicePartita;
	
	private SquadraVO casa;
	private SquadraVO trasferta;

	private SubdivisionVO subdivision;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public StadioVO getStadio() {
		return stadio;
	}
	public void setStadio(StadioVO stadio) {
		this.stadio = stadio;
	}
	public String getCodicePartita() {
		return codicePartita;
	}
	public void setCodicePartita(String codicePartita) {
		this.codicePartita = codicePartita;
	}
	public SquadraVO getCasa() {
		return casa;
	}
	public void setCasa(SquadraVO casa) {
		this.casa = casa;
	}
	public SquadraVO getTrasferta() {
		return trasferta;
	}
	public void setTrasferta(SquadraVO trasferta) {
		this.trasferta = trasferta;
	}
	public SubdivisionVO getSubdivision() {
		return subdivision;
	}
	public void setSubdivision(SubdivisionVO subdivision) {
		this.subdivision = subdivision;
	}
}
