package worldcup.orm.vo;

import java.util.Date;

public class PartitaVO {

	private Long id;

	private Date data;
	private StadioVO stadio;	
	private String codicePartita;
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
}
