package worldcup.impl.converter;

import worldcup.core.model.Team;
import worldcup.model.Squadra;

public class SquadraConverter {

	public static Squadra toRsModel(Team dto) {
		Squadra rsModel = new Squadra();
		
		rsModel.setBandiera(dto.getBandiera());
		rsModel.setNome(dto.getNome());
		
		return rsModel;
	}
}
