package worldcup.core;

import java.util.List;

public class Grafico extends JsonSerializable{

	/*
	 * {"coloriAutomatici":true,"xAxisGridLines":true,"titolo":"Distribuzione Giornaliera per Servizio ",
	 * "sottotitolo":"Numero Transazioni  (dal 19 maggio 2018 al 18 giugno 2018 )",
	 * "clickItemLegenda":false,"valoreRealeTorta":true,"colonneLegenda":16,"limiteColonneLegenda":16,"noData":"Non esistono transazioni per il periodo selezionato"}"
	 * */
	private List<Distribuzione> dati = null;
	private boolean coloriAutomatici = true;
	private boolean xAxisGridLines = true;
	private String titolo = null;
	private String sottotitolo = null;
	private boolean clickItemLegenda = false;
	private boolean valoreRealeTorta = true;
	private int colonneLegenda = 16;
	private int limiteColonneLegenda = 16;
	public List<Distribuzione> getDati() {
		return dati;
	}
	public void setDati(List<Distribuzione> dati) {
		this.dati = dati;
	}
	public boolean isColoriAutomatici() {
		return coloriAutomatici;
	}
	public void setColoriAutomatici(boolean coloriAutomatici) {
		this.coloriAutomatici = coloriAutomatici;
	}
	public boolean isxAxisGridLines() {
		return xAxisGridLines;
	}
	public void setxAxisGridLines(boolean xAxisGridLines) {
		this.xAxisGridLines = xAxisGridLines;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getSottotitolo() {
		return sottotitolo;
	}
	public void setSottotitolo(String sottotitolo) {
		this.sottotitolo = sottotitolo;
	}
	public boolean isClickItemLegenda() {
		return clickItemLegenda;
	}
	public void setClickItemLegenda(boolean clickItemLegenda) {
		this.clickItemLegenda = clickItemLegenda;
	}
	public boolean isValoreRealeTorta() {
		return valoreRealeTorta;
	}
	public void setValoreRealeTorta(boolean valoreRealeTorta) {
		this.valoreRealeTorta = valoreRealeTorta;
	}
	public int getColonneLegenda() {
		return colonneLegenda;
	}
	public void setColonneLegenda(int colonneLegenda) {
		this.colonneLegenda = colonneLegenda;
	}
	public int getLimiteColonneLegenda() {
		return limiteColonneLegenda;
	}
	public void setLimiteColonneLegenda(int limiteColonneLegenda) {
		this.limiteColonneLegenda = limiteColonneLegenda;
	}
	
	
}
