package worldcup.impl.converter;

import worldcup.model.Pronostico;
import worldcup.model.PronosticoRisultato;
import worldcup.orm.vo.DatiPartitaVO;
import worldcup.orm.vo.PronosticoVO;

public class PronosticoConverter {

	public static Pronostico toRsModel(PronosticoVO dto, Integer punti) {
		Pronostico rsModel = new Pronostico();
		
		rsModel.setGiocatore(GiocatoreConverter.toRsModel(dto.getGiocatore()));
		rsModel.setPunti(punti);
//		rsModel.setSquadraVincente(SquadraConverter.toRsModel(dto.getTorneo().getPronosticoUfficiale())); //TODO SQUADRA VINCENTE
		
		return rsModel;
	}
	
	public static PronosticoRisultato toRsModel(DatiPartitaVO dto) {
		PronosticoRisultato rsModel = new PronosticoRisultato();
		
		rsModel.setGoalAway(dto.getGoalTrasferta());
		rsModel.setGoalHome(dto.getGoalCasa());
		
		return rsModel;
	}
}
