package worldcup.business;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

import worldcup.dao.repositories.DatiPartitaRepository;
import worldcup.dao.repositories.GiocatoreRepository;
import worldcup.dao.repositories.PartitaRepository;
import worldcup.dao.repositories.PronosticoRepository;
import worldcup.dao.repositories.SquadraRepository;
import worldcup.dao.repositories.StadioRepository;
import worldcup.dao.repositories.SubdivisionRepository;
import worldcup.dao.repositories.TorneoRepository;

public class AbstractBD {
	
	@Autowired
	protected JpaTransactionManager txManager;
	
	@Autowired
	protected GiocatoreRepository giocatoreRepository;
	@Autowired
	protected PartitaRepository partitaRepository;
	@Autowired
	protected DatiPartitaRepository datiPartitaRepository;
	@Autowired
	protected PronosticoRepository pronosticoRepository;
	@Autowired
	protected SquadraRepository squadraRepository;
	@Autowired
	protected StadioRepository stadioRepository;
	@Autowired
	protected SubdivisionRepository subdivisionRepository;
	@Autowired
	protected TorneoRepository torneoRepository;

	@Autowired
	public void setJpaTransactionManager(JpaTransactionManager txManager){
		this.txManager = txManager;
	}

	// Questi metodi potranno diventare protected se non vogliamo che ci si acceda
	// dai vari ServiceImpl
	public <T> T runTransaction(Supplier<T> supplier) {

		TransactionStatus transaction = null;
		try {
			TransactionTemplate template = new TransactionTemplate(this.txManager);
			transaction = this.txManager.getTransaction(template);
			T ret = supplier.get();
			this.txManager.commit(transaction);
			return ret;

		} catch (Exception e) {
			if (transaction != null && !transaction.isCompleted()) {
				this.txManager.rollback(transaction);
			}
			// TODO: Catchare le ecezioni relative ai metodi commit,rollback e
			// getTransaction
			throw (e);
		}
	}

	public void runTransaction(Runnable runnable) {

		TransactionStatus transaction = null;
		try {
			TransactionTemplate template = new TransactionTemplate(this.txManager);
			transaction = this.txManager.getTransaction(template);
			runnable.run();
			this.txManager.commit(transaction);
		} catch (Exception e) {
			if (transaction != null && !transaction.isCompleted()) {
				this.txManager.rollback(transaction);
			}
			// TODO: Catchare le ecezzioni relative ai metodi commit,rollback e
			// getTransaction
			throw (e);
		}

	}


}
