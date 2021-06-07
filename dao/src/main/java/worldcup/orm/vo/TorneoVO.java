package worldcup.orm.vo;

import java.util.HashSet;
import java.util.Set;

public class TorneoVO {

	private Long id;
	private String nome;
	
	private Set<PronosticoVO> pronostici = new HashSet<>();
	private PronosticoVO pronosticoUfficiale;
	private Set<SubdivisionVO> subdivisions = new HashSet<>();
	
	private byte[] regole;
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
	public Set<PronosticoVO> getPronostici() {
		return pronostici;
	}
	public void setPronostici(Set<PronosticoVO> pronostici) {
		this.pronostici = pronostici;
	}
	public Set<SubdivisionVO> getSubdivisions() {
		return subdivisions;
	}
	public void setSubdivisions(Set<SubdivisionVO> subdivisions) {
		this.subdivisions = subdivisions;
	}
	public byte[] getRegole() {
		return regole;
	}
	public void setRegole(byte[] regole) {
		this.regole = regole;
	}
	public PronosticoVO getPronosticoUfficiale() {
		return pronosticoUfficiale;
	}
	public void setPronosticoUfficiale(PronosticoVO pronosticoUfficiale) {
		this.pronosticoUfficiale = pronosticoUfficiale;
	}
	
}
