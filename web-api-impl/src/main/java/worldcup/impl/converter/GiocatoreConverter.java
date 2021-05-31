package worldcup.impl.converter;

import worldcup.core.model.Player;
import worldcup.model.Giocatore;

public class GiocatoreConverter {

	
	public static Giocatore toRsModel(Player dto) {
		Giocatore rsModel = new Giocatore();
		
		rsModel.setCategoria(dto.getCategoria());
		rsModel.setIdGiocatore(dto.getId());
		rsModel.setLink(dto.getLink());
		rsModel.setNome(dto.getNome());
		
		return rsModel;
	}
}
