package worldcup.business;

import java.util.Optional;

import org.springframework.stereotype.Service;

import worldcup.business.calculator.TorneoUtils;
import worldcup.dao.filters.GiocatoreFilter;
import worldcup.dao.filters.SquadraFilter;
import worldcup.dao.filters.TorneoFilter;
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
		TorneoFilter filter = new TorneoFilter();
		filter.setNome(Optional.of(nome));
		return this.torneoRepository.count(filter) > 0;
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

	public void save(PartitaVO partita) {
		this.partitaRepository.save(partita);
	}

	public void save(DatiPartitaVO dpVO) {
		this.datiPartitaRepository.save(dpVO);
	}

	public void delete(DatiPartitaVO dpVO) {
		this.datiPartitaRepository.delete(dpVO);
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

	public SquadraVO getSquadra(String squadra) {
		SquadraFilter sf = new SquadraFilter();
		sf.setNomeLike(Optional.of(squadra));
		return this.squadraRepository.findOne(sf).orElseThrow(() -> new RuntimeException("Squadra ["+squadra+"] non trovata"));
	}

	public void updatePartite(TorneoVO torneo) {
		TorneoVO torneoPronosticato = TorneoUtils.getTorneoPronosticato(torneo.getPronosticoUfficiale());
		
		for(SubdivisionVO s: torneoPronosticato.getSubdivisions()) {
			for(PartitaVO p2: s.getPartite()) {
				PartitaVO partita = TorneoUtils.findPartita(p2.getCodicePartita(), torneo);
				PartitaVO p = TorneoUtils.findPartita(p2.getCodicePartita(), torneoPronosticato);

				boolean update = false;
				if(p.getCasa() != null) {
					if(partita.getCasa() == null || !partita.getCasa().getId().equals(p.getCasa().getId())) {
						partita.setCasa(p.getCasa());
						update = true;
					}
				} else {
					if(partita.getCasa() != null) {
						partita.setCasa(null);
						update = true;
					}
				}
				if(p.getTrasferta() != null) {
					if(partita.getTrasferta() == null || !partita.getTrasferta().getId().equals(p.getTrasferta().getId())) {
						partita.setTrasferta(p.getTrasferta());
						update = true;
					}
				} else {
					if(partita.getTrasferta() != null) {
						partita.setTrasferta(null);
						update = true;
					}
				}

				if(update) {
					this.save(partita);
				}
			}
			
		}
	}
}
