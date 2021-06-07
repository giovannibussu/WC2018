package worldcup.dao.repositories;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import worldcup.orm.vo.GiocatoreVO;

@Repository
public interface GiocatoreRepository extends JpaRepositoryImplementation<GiocatoreVO, Long>{

}