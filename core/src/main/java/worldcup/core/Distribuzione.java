package worldcup.core;

public class Distribuzione extends JsonSerializable {

	private String label;
	private Integer value;
	public String getLabel() {
		return label;
	}
	public void setLabel(String risultato) {
		this.label = risultato;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer numero) {
		this.value = numero;
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
		return "label "+label+" value "+value+" tooltip " + tooltip;
	}
}
