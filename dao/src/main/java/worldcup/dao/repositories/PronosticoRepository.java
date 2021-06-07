package worldcup.dao.repositories;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import worldcup.orm.vo.PronosticoVO;

@Repository
public interface PronosticoRepository extends JpaRepositoryImplementation<PronosticoVO, Long>{

}