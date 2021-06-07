package worldcup.impl.converter;

import worldcup.model.PronosticoPartita;
import worldcup.orm.vo.DatiPartitaVO;
import worldcup.orm.vo.GiocatoreVO;
import worldcup.orm.vo.PartitaVO;

public class PronosticoPartitaConverter {

	public static PronosticoPartita toRsModel(PartitaVO partita, DatiPartitaVO dp, GiocatoreVO playerDto) {
		PronosticoPartita rsModel = new PronosticoPartita();
		
		rsModel.setGiocatore(GiocatoreConverter.toRsModel(playerDto));
		rsModel.setPartita(PartitaConverter.toRsModel(partita, dp));
		rsModel.setPronostico(PronosticoConverter.toRsModel(dp));

		return rsModel;
	}
}
