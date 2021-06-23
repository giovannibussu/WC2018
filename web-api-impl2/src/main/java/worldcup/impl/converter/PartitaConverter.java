package worldcup.impl.converter;

import java.util.Locale;
import java.util.Optional;

import org.joda.time.DateTime;
import org.springframework.format.Formatter;

import worldcup.business.calculator.TorneoUtils;
import worldcup.model.Partita;
import worldcup.model.Risultato;
import worldcup.model.RisultatoPartita;
import worldcup.orm.vo.DatiPartitaVO;
import worldcup.orm.vo.PartitaVO;

public class PartitaConverter {

	public static Partita toRsModel(PartitaVO partita, Optional<DatiPartitaVO> dp, Formatter<DateTime> formatter) {
		Partita rsModel = new Partita();

		rsModel.setCasa(SquadraConverter.toRsModel(partita.getCasa()));
		rsModel.setData(formatter.print(new DateTime(partita.getData()), Locale.getDefault()));
		rsModel.setIdPartita(partita.getCodicePartita());
		rsModel.setDescrizione(partita.getDescrizione());

		rsModel.setStadio(StadioConverter.toRsModel(partita.getStadio()));
		rsModel.setTrasferta(SquadraConverter.toRsModel(partita.getTrasferta()));

		if (dp.isPresent()) {
			rsModel.setRisultato(toRisultatoPartitaRsModel(dp.get()));
		}
		return rsModel;
	}

	public static RisultatoPartita toRisultatoPartitaRsModel(DatiPartitaVO dp) {
		RisultatoPartita rsModel = new RisultatoPartita();

		rsModel.setGoalCasa(dp.getGoalCasa());
		rsModel.setGoalTrasferta(dp.getGoalTrasferta());

		String risultato1x2 = TorneoUtils.getRisultato1x2(dp, true);

		if (risultato1x2.equals("1")) {
			rsModel.setRisultatoFinale(Risultato._1);
		} else if (risultato1x2.equals("2")) {
			rsModel.setRisultatoFinale(Risultato._2);
		} else {
			rsModel.setRisultatoFinale(Risultato.X);
		}

		return rsModel;
	}

}
