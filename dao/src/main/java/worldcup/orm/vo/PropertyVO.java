package worldcup.orm.vo;

public class PropertyVO {
	public PropertyVO(String name, String value) {
		this.nome = name;
		this.valore= value;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getValore() {
		return valore;
	}
	public void setValore(String valore) {
		this.valore = valore;
	}
	private Long id;
	private String nome;
	private String valore;
}
