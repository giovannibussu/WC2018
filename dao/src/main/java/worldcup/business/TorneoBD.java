package worldcup.business;

import worldcup.orm.vo.DatiPartitaVO;
import worldcup.orm.vo.TorneoVO;

public class TorneoBD extends AbstractBD {

	public TorneoVO findByName(String nome) {
		return this.torneoRepository.findByNome(nome);
	}
	
	public void updateRisultatoPartita(TorneoVO torneo, DatiPartitaVO dpVO) {
		this.partitaRepository.save(dpVO.getPartita());
	}
}
