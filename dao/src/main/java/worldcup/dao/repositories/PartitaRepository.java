package worldcup.dao.repositories;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import worldcup.orm.vo.PartitaVO;

@Repository
public interface PartitaRepository extends JpaRepositoryImplementation<PartitaVO, Long>{

}