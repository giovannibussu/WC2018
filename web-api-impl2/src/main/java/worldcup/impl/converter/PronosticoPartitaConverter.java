package worldcup.impl.converter;

import worldcup.model.PronosticoPartita;
import worldcup.orm.vo.GiocatoreVO;
import worldcup.orm.vo.SquadraVO;

public class PronosticoPartitaConverter {

	public static PronosticoPartita toRsModel(SquadraVO dto, GiocatoreVO playerDto) {
		PronosticoPartita rsModel = new PronosticoPartita();
		
		rsModel.setGiocatore(GiocatoreConverter.toRsModel(playerDto));
//		rsModel.setPartita(PartitaConverter.toRsModel(dto));
//		rsModel.setPronostico(PronosticoConverter.toRsModel(dto));
		//TODO ???
		return rsModel;
	}
}
