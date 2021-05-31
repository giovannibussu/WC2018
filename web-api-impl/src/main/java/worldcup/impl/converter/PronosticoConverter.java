package worldcup.impl.converter;

import worldcup.model.Pronostico;
import worldcup.model.PronosticoRisultato;

public class PronosticoConverter {

	public static Pronostico toRsModel(worldcup.core.Pronostico dto, Integer punti) {
		Pronostico rsModel = new Pronostico();
		
		rsModel.setGiocatore(GiocatoreConverter.toRsModel(dto.getPlayer()));
		rsModel.setPunti(punti);
		rsModel.setSquadraVincente(SquadraConverter.toRsModel(dto.getTorneo().getWinner()));
		
		return rsModel;
	}
	
	public static PronosticoRisultato toRsModel(worldcup.core.model.Match dto) {
		PronosticoRisultato rsModel = new PronosticoRisultato();
		
		rsModel.setGoalAway(dto.getResult().getGoalsAway());
		rsModel.setGoalHome(dto.getResult().getGoalsHome());
		
		return rsModel;
	}
}
