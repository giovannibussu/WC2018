package worldcup.orm.vo;

public class DatiPartitaVO {

	private Long id;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public PartitaVO getPartita() {
		return partita;
	}
	public void setPartita(PartitaVO partita) {
		this.partita = partita;
	}
	private SquadraVO casa;
	private SquadraVO trasferta;
	private Integer goalCasa;
	private Integer goalTrasferta;
	private PartitaVO partita;

}
