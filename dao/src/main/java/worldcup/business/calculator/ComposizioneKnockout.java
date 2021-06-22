package worldcup.business.calculator;

import java.util.HashSet;
import java.util.Set;

import worldcup.orm.vo.PartitaVO;
import worldcup.orm.vo.SquadraVO;
import worldcup.orm.vo.SubdivisionVO;


public class ComposizioneKnockout {

	public SubdivisionVO getPartite(KnockoutResult knockout, SubdivisionVO subdivisionInput) {
		
		Set<PartitaVO> partite = new HashSet<>();
		
		SubdivisionVO subdivision = new SubdivisionVO();
		subdivision.setNome(subdivisionInput.getNome());
		subdivision.setTipo(subdivisionInput.getTipo());
		for(PartitaVO p : subdivisionInput.getPartite()) {
			PartitaVO partita = getPartita(knockout, p);
			partita.setCodicePartita(p.getCodicePartita());
			partite.add(partita);
		}
		subdivision.setPartite(partite);
		return subdivision;
	}

	private PartitaVO getPartita(KnockoutResult girone, PartitaVO partitaInput) {
		PartitaVO partita = new PartitaVO();
		
		partita.setData(partitaInput.getData());
		partita.setDescrizione(partitaInput.getDescrizione());
		partita.setId(partitaInput.getId());
		partita.setStadio(partitaInput.getStadio());
		partita.setSubdivision(partitaInput.getSubdivision());

		boolean isWinnerCasa = partitaInput.getCodiceCalcoloCasa().charAt(0) == 'W'; 
		boolean isWinnerTrasferta = partitaInput.getCodiceCalcoloTrasferta().charAt(0) == 'W'; 

		String partitaCasa = partitaInput.getCodiceCalcoloCasa().substring(1);
		String partitaTrasferta = partitaInput.getCodiceCalcoloTrasferta().substring(1);
		
		SquadraVO squadraCasa = null;
		if(isWinnerCasa) {
			squadraCasa = girone.getWinners().get(partitaCasa);
		} else {
			squadraCasa = girone.getLosers().get(partitaCasa);
		}
		
		partita.setCasa(squadraCasa);

		SquadraVO squadraTrasferta = null;
		if(isWinnerTrasferta) {
			squadraTrasferta = girone.getWinners().get(partitaTrasferta);
		} else {
			squadraTrasferta = girone.getLosers().get(partitaTrasferta);
		}
		partita.setTrasferta(squadraTrasferta);
		
		return partita;
		
	}
}
