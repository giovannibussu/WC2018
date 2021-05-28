package worldcup.main;

import java.text.ParseException;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.springframework.format.Formatter;

public class DateTimeFormatter implements Formatter<DateTime> {

	private org.joda.time.format.DateTimeFormatter formatter;
	
	public DateTimeFormatter(String pattern) {
		this.formatter = new DateTimeFormatterBuilder().appendPattern(pattern).toFormatter();
	}

	@Override
	public DateTime parse(String text, Locale locale) throws ParseException { 
		return DateTime.parse(text, this.formatter); 

	} 
	@Override
	public String print(DateTime object, Locale locale) {
		return object.toString(this.formatter);
	} 
}
