package worldcup.dao.repositories;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import worldcup.orm.vo.SquadraVO;

@Repository
public interface SquadraRepository extends JpaRepositoryImplementation<SquadraVO, Long>{

}