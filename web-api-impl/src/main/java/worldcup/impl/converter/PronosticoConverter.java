package worldcup.impl.converter;

import worldcup.model.Pronostico;

public class PronosticoConverter {

	public static Pronostico toRsModel(worldcup.core.Pronostico dto, Integer punti) {
		Pronostico rsModel = new Pronostico();
		
		rsModel.setGiocatore(GiocatoreConverter.toRsModel(dto.getPlayer()));
		rsModel.setPunti(punti);
		rsModel.setSquadraVincente(SquadraConverter.toRsModel(dto.getTorneo().getWinner()));
		
		return rsModel;
	}
}
