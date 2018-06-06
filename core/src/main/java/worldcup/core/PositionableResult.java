package worldcup.core;

public class PositionableResult {

	
	public PositionableResult() {
		passaggioTurno =0;
		posizioneTabellone =0;
		risultato =0;
		risultatoEsatto =0;
	}
	
	private int passaggioTurno;
	private int posizioneTabellone;
	private int risultato;
	private int risultatoEsatto;
	public int getPassaggioTurno() {
		return passaggioTurno;
	}
	public void addPassaggioTurno() {
		this.passaggioTurno++;
	}
	public int getPosizioneTabellone() {
		return posizioneTabellone;
	}
	public void addPosizioneTabellone() {
		this.posizioneTabellone++;
	}
	public int getRisultato() {
		return risultato;
	}
	public void addRisultato() {
		this.risultato++;
	}
	public int getRisultatoEsatto() {
		return risultatoEsatto;
	}
	public void addRisultatoEsatto() {
		this.risultatoEsatto++;
	}
	
}
