package worldcup;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.format.Formatter;

@SpringBootApplication
//@EnableJpaRepositories(entityManagerFactoryRef = "entityManager",
//transactionManagerRef = "transactionManager",
//basePackages = {"worldcup.dao.repositories"})
public class GiochinoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GiochinoApplication.class, args);
	}

	@Value("${spring.mvc.date-format:#{null}}")
	String datePattern;

	@Bean
	@ConditionalOnProperty(prefix = "spring.mvc", name = "date-format")
	public Formatter<LocalDate> localDateFormatter() {
		return new LocalDateFormatter(this.datePattern);
	}

	@Value("${spring.mvc.datetime-format:#{null}}")
	String datetimePattern;

	@Bean
	@ConditionalOnProperty(prefix = "spring.mvc", name = "datetime-format")
	public Formatter<DateTime> dateTimeFormatter() {
		return new DateTimeFormatter(this.datetimePattern);
	}
}
