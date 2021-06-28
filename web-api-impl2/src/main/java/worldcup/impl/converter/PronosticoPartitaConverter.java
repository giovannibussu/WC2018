package worldcup.impl.converter;

import org.springframework.format.Formatter;

import java.util.Optional;

import org.joda.time.DateTime;

import worldcup.model.PronosticoPartita;
import worldcup.orm.vo.DatiPartitaVO;
import worldcup.orm.vo.GiocatoreVO;
import worldcup.orm.vo.PartitaVO;

public class PronosticoPartitaConverter {

	public static PronosticoPartita toRsModel(PartitaVO partita, DatiPartitaVO dp, GiocatoreVO playerDto, Formatter<DateTime> formatter, boolean isReverse) {
		PronosticoPartita rsModel = new PronosticoPartita();
		
		rsModel.setGiocatore(GiocatoreConverter.toRsModel(playerDto));
		rsModel.setPartita(PartitaConverter.toRsModel(partita, Optional.of(dp), formatter, isReverse));
		rsModel.setPronostico(PronosticoConverter.toRsModel(dp, isReverse));

		return rsModel;
	}
}
