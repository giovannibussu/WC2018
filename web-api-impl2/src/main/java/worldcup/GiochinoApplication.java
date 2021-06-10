package worldcup;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.format.Formatter;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import worldcup.business.TorneoBD;
import worldcup.orm.vo.GiocatoreVO;
import worldcup.orm.vo.PartitaVO;
import worldcup.orm.vo.PronosticoVO;
import worldcup.orm.vo.SquadraVO;
import worldcup.orm.vo.StadioVO;
import worldcup.orm.vo.SubdivisionVO;
import worldcup.orm.vo.SubdivisionVO.TIPO;
import worldcup.orm.vo.TorneoVO;

@SpringBootApplication
@EnableJpaRepositories(entityManagerFactoryRef = "entityManager",
transactionManagerRef = "transactionManager",
basePackages = {"worldcup.dao.repositories"})
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

	@Value("${spring.datasource.jndiName}")
	String jndiName;

	@Bean(name ="entityManager") 
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName(this.jndiName);
		bean.setProxyInterface(DataSource.class);
		bean.setLookupOnStartup(false);
		try {
			bean.afterPropertiesSet();
		} catch (IllegalArgumentException | NamingException e) {
			throw new RuntimeException(e);
		}
		DataSource dSource = (DataSource) bean.getObject();

		entityManagerFactoryBean.setDataSource(dSource);
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPersistenceUnitName("pronostici");
		entityManagerFactoryBean.setPackagesToScan("worldcup.orm.vo");
		//additional config of factory

		return entityManagerFactoryBean;
	}

	@Bean(name ="transactionManager") 
	public JpaTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;		  
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
		return builder -> {
			builder.simpleDateFormat(datetimePattern);
			builder.serializers(new LocalDateSerializer(java.time.format.DateTimeFormatter.ofPattern(datePattern)));
			builder.serializers(new LocalDateTimeSerializer(java.time.format.DateTimeFormatter.ofPattern(datetimePattern)));
		};
	}



	@Bean
	public String initDB(TorneoBD torneoBD) throws IOException {

		if(!torneoBD.existsByName("EURO2021")) {
			torneoBD.runTransaction(() -> {
				TorneoVO torneo = new TorneoVO();

				torneo.setNome("EURO2021");

				InputStream is = GiochinoApplication.class.getResourceAsStream("/gironiEuro2020.csv");
				InputStream isSquadre = GiochinoApplication.class.getResourceAsStream("/teams.json");

				ObjectMapper om = new ObjectMapper();
				
				Map<String, SquadraVO> squadre = new HashMap<>();
				try {
					JsonNode tree = om.reader().readTree(isSquadre);
					
					Iterator<JsonNode> itSq = tree.elements();
					while(itSq.hasNext()) {
						JsonNode squadraNode = itSq.next();
						
						String nomeSquadra = squadraNode.get("nome").asText();
						String bandieraSquadra = squadraNode.get("bandiera").asText();
						Integer rankingSquadra = Integer.parseInt(squadraNode.get("fairPlay").asText());
						SquadraVO squadra = new SquadraVO();
						squadra.setNome(nomeSquadra);
						squadra.setBandiera(bandieraSquadra);
						squadra.setRankingFifa(rankingSquadra);
						torneoBD.create(squadra);

						squadre.put(nomeSquadra, squadra);
						
					}

				} catch (IOException e1) {
					throw new RuntimeException("Errore durante la lettura delle squadre: " + e1.getMessage(), e1);
				}
				
				String gironi;
				try {
					gironi = new String(is.readAllBytes());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}

				String[] gironiLines = gironi.split("\n");
				Map<String, SubdivisionVO> gironiMap = new HashMap<>();

				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy.hh:mm");

				for(String partita: gironiLines) {
					String[] partitaFields = partita.split(";");

					String dataKey = partitaFields[2];
					String gironeKey = partitaFields[1];
					String codicePartita = partitaFields[0];
					String nomeSquadraCasa = partitaFields[3];
					String nomeSquadraTrasferta = partitaFields[4];

					SubdivisionVO girone = null;
					if(!gironiMap.containsKey(gironeKey)) {
						girone = new SubdivisionVO();
						girone.setNome(gironeKey);
						girone.setTipo(TIPO.GIRONE);
						gironiMap.put(gironeKey, girone);
					} else {
						girone = gironiMap.get(gironeKey);
					}


					PartitaVO partitaVO = new PartitaVO();
					partitaVO.setCodicePartita(codicePartita);

					if(squadre.containsKey(nomeSquadraCasa)) {
						partitaVO.setCasa(squadre.get(nomeSquadraCasa));
					} else {
						throw new RuntimeException("Squadra ["+nomeSquadraCasa+"] non registrata");
					}

					if(squadre.containsKey(nomeSquadraTrasferta)) {
						partitaVO.setTrasferta(squadre.get(nomeSquadraTrasferta));
					} else {
						throw new RuntimeException("Squadra ["+nomeSquadraTrasferta+"] non registrata");
					}

					try {
						partitaVO.setData(sdf.parse(dataKey));
					} catch (ParseException e) {
						System.err.println(e.getMessage());
						e.printStackTrace(System.err);
						partitaVO.setData(new Date());
					}
					StadioVO stadio1 = new StadioVO(); //TODO
					stadio1.setCitta("ROMA");
					stadio1.setNome("NOME");
					stadio1.setLink("http://");

					torneoBD.create(stadio1);

					partitaVO.setStadio(stadio1);
					girone.getPartite().add(partitaVO);

					partitaVO.setSubdivision(girone);
				}

				PronosticoVO pronosticoUfficiale = new PronosticoVO();
				GiocatoreVO giocatore = new GiocatoreVO();
				giocatore.setNome("UFFICIALE");

				pronosticoUfficiale.setGiocatore(giocatore);
				pronosticoUfficiale.setIdPronostico("ufficiale");

				torneo.setPronosticoUfficiale(pronosticoUfficiale);

				torneoBD.create(giocatore);
				torneoBD.create(pronosticoUfficiale);
				torneoBD.create(torneo);

				for(SubdivisionVO girone: gironiMap.values()) {
					Map<String, SquadraVO> squadreGirone = new HashMap<>();
					for(PartitaVO p: girone.getPartite()) {
						squadreGirone.put(p.getCasa().getNome(), p.getCasa());
						squadreGirone.put(p.getTrasferta().getNome(), p.getTrasferta());
					}

					girone.setSquadre(squadreGirone.values().stream().collect(Collectors.toSet()));

					for(PartitaVO p: girone.getPartite()) {
						torneoBD.create(p);
					}
					girone.setTorneo(torneo);
					torneoBD.create(girone);
				}

			});
		}
		return "";
	}


}
