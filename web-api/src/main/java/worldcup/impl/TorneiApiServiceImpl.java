package worldcup.impl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import worldcup.api.TorneiApi;
import worldcup.main.InternalException;
import worldcup.model.Grafico;
import worldcup.model.Partita;
import worldcup.model.Pronostico;
import worldcup.model.PronosticoPartita;
import worldcup.model.RisultatoPartita;
import worldcup.model.TipoDistribuzione;
import worldcup.model.Torneo;

@RestController
public class TorneiApiServiceImpl implements TorneiApi {

	private Logger logger = LoggerFactory.getLogger(TorneiApiServiceImpl.class);

	public ResponseEntity<List<Pronostico>> getClassifica(String idTorneo) {
		try {
			
			List<Pronostico> lst = new ArrayList<>();
			return ResponseEntity.ok(lst);
		} catch(RuntimeException e) {
			this.logger.error("Invocazione terminata con errore '4xx': " +e.getMessage(),e);
			throw e;
		}
		catch(Throwable e) {
			this.logger.error("Invocazione terminata con errore: " +e.getMessage(),e);
			throw new InternalException(e);
		}
	}

	public ResponseEntity<Grafico> getDistribuzionePartita(String idTorneo, String idPartita, TipoDistribuzione tipo) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResponseEntity<Partita> getPartita(String idTorneo, String idPartita) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResponseEntity<List<Pronostico>> getPronostici(String idTorneo) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResponseEntity<List<PronosticoPartita>> getPronosticiPartita(String idTorneo, String idPartita) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResponseEntity<Torneo> getTorneo(String idTorneo) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResponseEntity<List<Partita>> listPartite(String idTorneo, Integer limit, Long offset, DateTime dataDa,
			DateTime dataA) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResponseEntity<List<Torneo>> listTornei(Integer offset) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResponseEntity<Partita> updateRisultatoPartita(String idTorneo, String idPartita,
			RisultatoPartita risultatoPartita) {
		// TODO Auto-generated method stub
		return null;
	}
}
