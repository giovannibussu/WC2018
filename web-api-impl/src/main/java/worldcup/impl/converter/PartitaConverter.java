package worldcup.impl.converter;

import org.joda.time.DateTime;

import worldcup.core.MatchResult;
import worldcup.core.MatchResult.RISULTATO;
import worldcup.core.model.Match;
import worldcup.core.model.Stadium;
import worldcup.model.Partita;
import worldcup.model.Risultato;
import worldcup.model.RisultatoPartita;

public class PartitaConverter {

	public static Partita toRsModel(Match dto) {
		Partita rsModel = new Partita();
		
		rsModel.setCasa(SquadraConverter.toRsModel(dto.getHome()));
//		rsModel.setData(new DateTime(dto.getDataMatch()));
		rsModel.setDescrizione(dto.getDescrizione());
		rsModel.setIdPartita(dto.getMatchId());
		rsModel.setRisultato(toRsModel(dto.getResult()));
		rsModel.setStadio(StadioConverter.toRsModel(Stadium.getStadiums().get( dto.getStadium())));
		rsModel.setTrasferta(SquadraConverter.toRsModel(dto.getAway()));
		
		return rsModel;
	}
	
	public static RisultatoPartita toRsModel(MatchResult dto) {
		if(dto == null) return null;
		RisultatoPartita rsModel = new RisultatoPartita();
		
		rsModel.setGoalCasa(dto.getGoalsHome());
		rsModel.setGoalTrasferta(dto.getGoalsAway());
		rsModel.setRisultatoFinale(toRsModel(dto.getRisultato()));
		
		return rsModel;
	}

	private static Risultato toRsModel(RISULTATO risultato) {
		if(risultato == null)
			return null;
		
		switch (risultato) {
		case X:
			return Risultato.X;
		case _1:
			return Risultato._1;
		case _2:
			return Risultato._2;
		}

		return null;
	}
}
