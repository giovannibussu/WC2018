package worldcup.impl.converter;

import worldcup.core.model.Stadium;
import worldcup.model.Stadio;

public class StadioConverter {


	public static Stadio toRsModel(Stadium dto) {
		Stadio rsModel = new Stadio();
		
		rsModel.setLink(dto.getLink());
		rsModel.setNome(dto.getNome());
		rsModel.setCitta(dto.getCitta());
		
		return rsModel;
	}
}
