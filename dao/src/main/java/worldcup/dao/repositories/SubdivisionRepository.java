package worldcup.dao.repositories;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import worldcup.orm.vo.SubdivisionVO;

@Repository
public interface SubdivisionRepository extends JpaRepositoryImplementation<SubdivisionVO, Long>{

}