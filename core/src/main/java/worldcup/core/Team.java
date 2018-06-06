package worldcup.core;

public class Team extends JsonSerializable {

	private String nome;
	private String id;
	private String bandiera;
	private int fairPlay;

	public int getFairPlay() {
		return fairPlay;
	}
	public void setFairPlay(int fairPlay) {
		this.fairPlay = fairPlay;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(!(obj instanceof Team))
			return false;
		
		return ((Team)obj).getNome().equals(this.nome);

	}
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBandiera() {
		return bandiera;
	}
	public void setBandiera(String bandiera) {
		this.bandiera = bandiera;
	}
	
}
