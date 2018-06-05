package worldcup.bean;

import java.io.Serializable;

public class Squadra implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id = null;
	private String nome = null;
	private String bandiera = null;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getBandiera() {
		return bandiera;
	}
	public void setBandiera(String bandiera) {
		this.bandiera = bandiera;
	}
	
}
