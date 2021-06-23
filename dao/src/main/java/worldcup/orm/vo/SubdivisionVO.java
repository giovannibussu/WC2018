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
//	private Set<PropertyVO> algoritmoTerze = new HashSet<>();
//	private Set<PropertyVO> opponentsTerze = new HashSet<>();
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

	public Set<PropertyVO> getOpponentsTerze() {
		Set<PropertyVO> opponentsTerze = new HashSet<>();
		
		opponentsTerze.add(new PropertyVO("B","0"));
		opponentsTerze.add(new PropertyVO("C","1"));
		opponentsTerze.add(new PropertyVO("E","2"));
		opponentsTerze.add(new PropertyVO("F","3"));

		return opponentsTerze;
	}

//	public void setOpponentsTerze(Set<PropertyVO> opponentsTerze) {
//		this.opponentsTerze = opponentsTerze;
//	}
//
	public Set<PropertyVO> getAlgoritmoTerze() {
		Set<PropertyVO> algoritmoTerze = new HashSet<>();
		
		algoritmoTerze.add(new PropertyVO("ABCD","ADBC"));
		algoritmoTerze.add(new PropertyVO("ABCE","AEBC"));
		algoritmoTerze.add(new PropertyVO("ABCF","AFBC"));
		algoritmoTerze.add(new PropertyVO("ABDE","DEAB"));
		algoritmoTerze.add(new PropertyVO("ABDF","DFAB"));
		algoritmoTerze.add(new PropertyVO("ABEF","EFBA"));
		algoritmoTerze.add(new PropertyVO("ACDE","EDCA"));
		algoritmoTerze.add(new PropertyVO("ACDF","FDCA"));
		algoritmoTerze.add(new PropertyVO("ACEF","EFCA"));
		algoritmoTerze.add(new PropertyVO("ADEF","EFDA"));
		algoritmoTerze.add(new PropertyVO("BCDE","EDBC"));
		algoritmoTerze.add(new PropertyVO("BCDF","FDCB"));
		algoritmoTerze.add(new PropertyVO("BCEF","FECB"));
		algoritmoTerze.add(new PropertyVO("BDEF","FEDB"));
		algoritmoTerze.add(new PropertyVO("CDEF","FEDC"));

		return algoritmoTerze;
	}

//	public void setAlgoritmoTerze(Set<PropertyVO> algoritmoTerze) {
//		this.algoritmoTerze = algoritmoTerze;
//	}
}
