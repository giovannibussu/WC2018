package worldcup.impl.converter;

import worldcup.model.Giocatore;
import worldcup.orm.vo.GiocatoreVO;

public class GiocatoreConverter {

	
	public static Giocatore toRsModel(GiocatoreVO dto) {
		Giocatore rsModel = new Giocatore();
		
		rsModel.setCategoria(dto.getTags());
//		rsModel.setIdGiocatore(dto.getId());
//		rsModel.setLink(dto.getLink()); //TODO ID GIOCATORE
		rsModel.setNome(dto.getNome());
		
		return rsModel;
	}
}
