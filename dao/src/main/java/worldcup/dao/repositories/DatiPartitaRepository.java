package worldcup.dao.repositories;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import worldcup.orm.vo.DatiPartitaVO;

@Repository
public interface DatiPartitaRepository extends JpaRepositoryImplementation<DatiPartitaVO, Long>{

}