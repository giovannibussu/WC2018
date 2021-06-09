package worldcup.orm.vo;

public class DatiPartitaVO {

	private Long id;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getGoalCasa() {
		return goalCasa;
	}
	public void setGoalCasa(Integer goalCasa) {
		this.goalCasa = goalCasa;
	}
	public Integer getGoalTrasferta() {
		return goalTrasferta;
	}
	public void setGoalTrasferta(Integer goalTrasferta) {
		this.goalTrasferta = goalTrasferta;
	}
	public String getCodicePartita() {
		return codicePartita;
	}
	public void setCodicePartita(String codicePartita) {
		this.codicePartita = codicePartita;
	}
	public PronosticoVO getPronostico() {
		return pronostico;
	}
	public void setPronostico(PronosticoVO pronostico) {
		this.pronostico = pronostico;
	}
	private Integer goalCasa;
	private Integer goalTrasferta;
	private String codicePartita;
	private PronosticoVO pronostico;

}
