package worldcup.core;

public class PositionableConfigurationFactory {

	public static PositionableConfiguration getGironeConfiguration() {
		PositionableConfiguration conf = new PositionableConfiguration();
		conf.setPassaggioTurno(5);
		conf.setPosizioneTabellone(3);
		conf.setRisultato(1);
		conf.setRisultatoEsatto(3);
		return conf;
	}
	public static PositionableConfiguration getOttaviConfiguration() {
		PositionableConfiguration conf = new PositionableConfiguration();
		conf.setPassaggioTurno(6);
		conf.setPosizioneTabellone(4);
		conf.setRisultato(3);
		conf.setRisultatoEsatto(7);
		return conf;
	}
	public static PositionableConfiguration getQuartiConfiguration() {
		PositionableConfiguration conf = new PositionableConfiguration();
		conf.setPassaggioTurno(7);
		conf.setPosizioneTabellone(5);
		conf.setRisultato(5);
		conf.setRisultatoEsatto(11);
		return conf;
	}
	public static PositionableConfiguration getSemifinaleConfiguration() {
		PositionableConfiguration conf = new PositionableConfiguration();
		conf.setPassaggioTurno(8);
		conf.setPosizioneTabellone(6);
		conf.setRisultato(6);
		conf.setRisultatoEsatto(15);
		return conf;
	}
	public static PositionableConfiguration getFinaleConfiguration() {
		PositionableConfiguration conf = new PositionableConfiguration();
		conf.setPassaggioTurno(0);
		conf.setPosizioneTabellone(0);
		conf.setRisultato(7);
		conf.setRisultatoEsatto(21);
		return conf;
	}
}
