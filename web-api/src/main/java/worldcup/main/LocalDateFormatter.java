package worldcup.main;

import java.text.ParseException;
import java.util.Locale;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.springframework.format.Formatter;

public class LocalDateFormatter implements Formatter<LocalDate> {

	private DateTimeFormatter formatter;
	
	public LocalDateFormatter(String pattern) {
		this.formatter = new DateTimeFormatterBuilder().appendPattern(pattern).toFormatter();
	}

	@Override
	public LocalDate parse(String text, Locale locale) throws ParseException { 
		return LocalDate.parse(text, this.formatter); 

	} 
	@Override
	public String print(LocalDate object, Locale locale) {
		StringBuffer sb = new StringBuffer();
		this.formatter.getPrinter().printTo(sb, object, locale);
		return  sb.toString();
	} 
}
