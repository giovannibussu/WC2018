package worldcup.dao.repositories;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import worldcup.orm.vo.TorneoVO;

@Repository
public interface TorneoRepository extends JpaRepositoryImplementation<TorneoVO, Long>{

	public TorneoVO findByNome(String nome);
}