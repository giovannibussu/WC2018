package worldcup.impl.converter;

import worldcup.model.Giocatore;
import worldcup.orm.vo.GiocatoreVO;

public class GiocatoreConverter {

	
	public static Giocatore toRsModel(GiocatoreVO dto) {
		Giocatore rsModel = new Giocatore();
		
		rsModel.setCategoria(dto.getTags());
		rsModel.setIdGiocatore(dto.getNome());
		rsModel.setNome(dto.getNome());
		
		return rsModel;
	}
}
