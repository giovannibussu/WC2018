package worldcup.impl.converter;

import worldcup.model.Squadra;
import worldcup.orm.vo.SquadraVO;

public class SquadraConverter {

	public static Squadra toRsModel(SquadraVO dto) {
		Squadra rsModel = new Squadra();
		
		rsModel.setBandiera(dto.getBandiera());
		rsModel.setNome(dto.getNome());
		
		return rsModel;
	}
}
