package worldcup.impl.converter;

import worldcup.core.model.Match;
import worldcup.core.model.Player;
import worldcup.model.PronosticoPartita;

public class PronosticoPartitaConverter {

	public static PronosticoPartita toRsModel(Match dto, Player playerDto) {
		PronosticoPartita rsModel = new PronosticoPartita();
		
		rsModel.setGiocatore(GiocatoreConverter.toRsModel(playerDto));
		rsModel.setPartita(PartitaConverter.toRsModel(dto));
		rsModel.setPronostico(PronosticoConverter.toRsModel(dto));
		
		return rsModel;
	}
}
