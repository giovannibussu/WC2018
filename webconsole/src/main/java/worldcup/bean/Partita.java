package worldcup.bean;

import java.io.Serializable;

public class Partita implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String data = null;
	private String ora =null;
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getOra() {
		return ora;
	}
	public void setOra(String ora) {
		this.ora = ora;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Squadra getSquadra1() {
		return squadra1;
	}
	public void setSquadra1(Squadra squadra1) {
		this.squadra1 = squadra1;
	}
	public Squadra getSquadra2() {
		return squadra2;
	}
	public void setSquadra2(Squadra squadra2) {
		this.squadra2 = squadra2;
	}
	public Stadio getStadio() {
		return stadio;
	}
	public void setStadio(Stadio stadio) {
		this.stadio = stadio;
	}
	public String getRisultato() {
		return risultato;
	}
	public void setRisultato(String risultato) {
		this.risultato = risultato;
	}
	private String id = null;
	private Squadra squadra1 =null;
	private Squadra squadra2 =null;
	private Stadio stadio = null;
	private String risultato = null;
	
}
