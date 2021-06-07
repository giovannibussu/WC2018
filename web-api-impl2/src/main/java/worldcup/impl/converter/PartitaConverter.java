package worldcup.impl.converter;

import org.joda.time.DateTime;

import worldcup.business.calculator.TorneoUtils;
import worldcup.model.Partita;
import worldcup.model.Risultato;
import worldcup.model.RisultatoPartita;
import worldcup.orm.vo.DatiPartitaVO;

public class PartitaConverter {

	public static Partita toRsModel(DatiPartitaVO dp) {
		Partita rsModel = new Partita();
		
		rsModel.setCasa(SquadraConverter.toRsModel(dp.getCasa()));
		rsModel.setData(new DateTime(dp.getPartita().getData()));
//		rsModel.setDescrizione(dp.getPartita().getDescrizione());
		rsModel.setIdPartita(dp.getPartita().getCodicePartita());
		rsModel.setRisultato(toRisultatoPartitaRsModel(dp));
		rsModel.setStadio(StadioConverter.toRsModel(dp.getPartita().getStadio()));
		rsModel.setTrasferta(SquadraConverter.toRsModel(dp.getTrasferta()));
		
		return rsModel;
	}
	
	public static RisultatoPartita toRisultatoPartitaRsModel(DatiPartitaVO dp) {
		RisultatoPartita rsModel = new RisultatoPartita();
		
		rsModel.setGoalCasa(dp.getGoalCasa());
		rsModel.setGoalTrasferta(dp.getGoalTrasferta());

		String risultato1x2 = TorneoUtils.getRisultato1x2(dp);

		if(risultato1x2.equals("1")) {
			rsModel.setRisultatoFinale(Risultato._1);
		} else if (risultato1x2.equals("2")) {
			rsModel.setRisultatoFinale(Risultato._2);
		} else  {
			rsModel.setRisultatoFinale(Risultato.X);
		}
		
		return rsModel;
	}

}
