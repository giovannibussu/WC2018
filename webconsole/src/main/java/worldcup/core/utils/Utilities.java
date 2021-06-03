package worldcup.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;

import worldcup.clients.model.Partita;

public class Utilities {
	
	

	public static String getDataMatchAsString(Date dataMatch) {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE dd/MM",Locale.ITALY);
		return sdf.format(dataMatch);
	}
	
	public static String getDataMatchAsString(DateTime dataMatch) {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE dd/MM",Locale.ITALY);
		return sdf.format(dataMatch);
	}
	
	public static String getOraMatchAsString(Date dataMatch) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm",Locale.ITALY);
		return sdf.format(dataMatch);
	}
	
	public static String getOraMatchAsString(DateTime dataMatch) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm",Locale.ITALY);
		return sdf.format(dataMatch);
	}
	
	public static String getMatchLabel(Partita partita) {
		return partita.getCasa().getNome() + " - " + partita.getTrasferta().getNome(); 
	}
}
