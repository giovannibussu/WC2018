package worldcup.impl.converter;

import worldcup.model.Stadio;
import worldcup.orm.vo.StadioVO;

public class StadioConverter {


	public static Stadio toRsModel(StadioVO dto) {
		Stadio rsModel = new Stadio();
		
		rsModel.setLink(dto.getLink());
		rsModel.setNome(dto.getNome());
		rsModel.setCitta(dto.getCitta());
		
		return rsModel;
	}
}
