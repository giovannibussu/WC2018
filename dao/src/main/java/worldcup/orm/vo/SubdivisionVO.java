package worldcup.orm.vo;

import java.util.HashSet;
import java.util.Set;

public class SubdivisionVO {
	
	public enum TIPO {GIRONE, OTTAVI, QUARTI, SEMIFINALE, FINALE}

	private Long id;

	private Set<PartitaVO> partite = new HashSet<>();
	private Set<SquadraVO> squadre = new HashSet<>();
	private TIPO tipo;
	private String nome;
	private TorneoVO torneo;
	private PunteggiVO punteggi;
	
	public Set<PartitaVO> getPartite() {
		return partite;
	}

	public void setPartite(Set<PartitaVO> partite) {
		this.partite = partite;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TIPO getTipo() {
		return tipo;
	}

	public void setTipo(TIPO tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<SquadraVO> getSquadre() {
		return squadre;
	}

	public void setSquadre(Set<SquadraVO> squadre) {
		this.squadre = squadre;
	}

	public TorneoVO getTorneo() {
		return torneo;
	}

	public void setTorneo(TorneoVO torneo) {
		this.torneo = torneo;
	}

	public PunteggiVO getPunteggi() {
		return punteggi;
	}

	public void setPunteggi(PunteggiVO punteggi) {
		this.punteggi = punteggi;
	}
}
