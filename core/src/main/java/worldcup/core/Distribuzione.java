package worldcup.core;

public class Distribuzione extends JsonSerializable {

	private String risultato;
	private Integer numero;
	public String getRisultato() {
		return risultato;
	}
	public void setRisultato(String risultato) {
		this.risultato = risultato;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public String getTooltip() {
		return tooltip;
	}
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	private String tooltip;
	
	@Override
	public String toString() {
		return "risultato "+risultato+" numero "+numero+" tooltip " + tooltip;
	}
}
