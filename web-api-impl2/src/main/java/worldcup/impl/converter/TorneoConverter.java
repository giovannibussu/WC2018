package worldcup.impl.converter;

import worldcup.model.Torneo;
import worldcup.orm.vo.TorneoVO;

public class TorneoConverter {

	public static Torneo getTorneo(TorneoVO torneoVO) {
		Torneo torneo = new Torneo();
		torneo.setIdTorneo(torneoVO.getNome());
		
		return torneo;
	}

}
