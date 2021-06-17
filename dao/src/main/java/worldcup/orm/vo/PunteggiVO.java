package worldcup.orm.vo;

public class PunteggiVO {

	private Long id;

	public Long getId() {
		return id;
	}

	private String identificativo;
	private int puntiPerRisultato;
	private int puntiPerRisultatoEsatto;
	private int puntiPerPassaggi;
	private int puntiPerPosizione;
	public int getPuntiPerRisultatoEsatto() {
		return puntiPerRisultatoEsatto;
	}
	public void setPuntiPerRisultatoEsatto(int puntiPerRisultatoEsatto) {
		this.puntiPerRisultatoEsatto = puntiPerRisultatoEsatto;
	}
	public int getPuntiPerPassaggi() {
		return puntiPerPassaggi;
	}
	public void setPuntiPerPassaggi(int puntiPerPassaggi) {
		this.puntiPerPassaggi = puntiPerPassaggi;
	}
	public int getPuntiPerPosizione() {
		return puntiPerPosizione;
	}
	public void setPuntiPerPosizione(int puntiPerPosizione) {
		this.puntiPerPosizione = puntiPerPosizione;
	}
	public int getPuntiPerRisultato() {
		return puntiPerRisultato;
	}
	public void setPuntiPerRisultato(int puntiPerRisultato) {
		this.puntiPerRisultato = puntiPerRisultato;
	}
	public String getIdentificativo() {
		return identificativo;
	}
	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
	}
}
