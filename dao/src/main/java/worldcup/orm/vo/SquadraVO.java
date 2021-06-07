package worldcup.orm.vo;

public class SquadraVO {

	private Long id;
	private String bandiera;
	private String nome;
	private Integer rankingFifa;
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getRankingFifa() {
		return rankingFifa;
	}
	public void setRankingFifa(Integer rankingFifa) {
		this.rankingFifa = rankingFifa;
	}
	public String getBandiera() {
		return bandiera;
	}
	public void setBandiera(String bandiera) {
		this.bandiera = bandiera;
	}
	
	
}
