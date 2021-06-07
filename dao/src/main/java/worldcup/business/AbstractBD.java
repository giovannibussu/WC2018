package worldcup.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;

import worldcup.dao.repositories.GiocatoreRepository;
import worldcup.dao.repositories.PartitaRepository;
import worldcup.dao.repositories.PronosticoRepository;
import worldcup.dao.repositories.SquadraRepository;
import worldcup.dao.repositories.StadioRepository;
import worldcup.dao.repositories.SubdivisionRepository;
import worldcup.dao.repositories.TorneoRepository;

public class AbstractBD {
	
	protected JpaTransactionManager txManager;
	
	protected GiocatoreRepository giocatoreRepository;
	protected PartitaRepository partitaRepository;
	protected PronosticoRepository pronosticoRepository;
	protected SquadraRepository squadraRepository;
	protected StadioRepository stadioRepository;
	protected SubdivisionRepository subdivisionRepository;
	protected TorneoRepository torneoRepository;

	@Autowired
	public void setJpaTransactionManager(JpaTransactionManager txManager){
		this.txManager = txManager;
	}

}
