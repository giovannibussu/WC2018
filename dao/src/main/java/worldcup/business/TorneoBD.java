package worldcup.business;

import java.util.Optional;

import org.springframework.stereotype.Service;

import worldcup.dao.filters.GiocatoreFilter;
import worldcup.orm.vo.DatiPartitaVO;
import worldcup.orm.vo.GiocatoreVO;
import worldcup.orm.vo.PartitaVO;
import worldcup.orm.vo.PronosticoVO;
import worldcup.orm.vo.SquadraVO;
import worldcup.orm.vo.StadioVO;
import worldcup.orm.vo.SubdivisionVO;
import worldcup.orm.vo.TorneoVO;

@Service
public class TorneoBD extends AbstractBD {

	public TorneoVO findByName(String nome) {
		return this.torneoRepository.findByNome(nome);
	}
	
	public boolean existsByName(String nome) {
		try {
			this.torneoRepository.findByNome(nome);
			return true;
		} catch(RuntimeException e) {
			return false;
		}
	}
	
	public void create(TorneoVO torneo) {
		this.torneoRepository.save(torneo);
	}
	public void create(GiocatoreVO giocatore) {
		this.giocatoreRepository.save(giocatore);
	}
	public void create(PronosticoVO pronostico) {
		this.pronosticoRepository.save(pronostico);
	}

	public void create(SquadraVO squadra) {
		this.squadraRepository.save(squadra);
	}

	public void create(PartitaVO partita) {
		this.partitaRepository.save(partita);
	}

	public void save(DatiPartitaVO dpVO) {
		this.datiPartitaRepository.save(dpVO);
	}

	public void create(StadioVO stadio1) {
		this.stadioRepository.save(stadio1);
	}

	public void create(SubdivisionVO girone) {
		this.subdivisionRepository.save(girone);
	}

	public GiocatoreVO getGiocatore(String idGiocatore) {
		GiocatoreFilter gf = new GiocatoreFilter();
		gf.setNome(Optional.of(idGiocatore));
		Optional<GiocatoreVO> findOne = this.giocatoreRepository.findOne(gf);
		
		if(findOne.isPresent()) {
			return findOne.get();
		} else {
			GiocatoreVO giocatore = new GiocatoreVO();
			giocatore.setNome(idGiocatore);
			this.giocatoreRepository.save(giocatore);
			
			return giocatore;
		}
	}
}
