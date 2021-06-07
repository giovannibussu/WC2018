package worldcup.dao.repositories;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import worldcup.orm.vo.StadioVO;

@Repository
public interface StadioRepository extends JpaRepositoryImplementation<StadioVO, Long>{

}