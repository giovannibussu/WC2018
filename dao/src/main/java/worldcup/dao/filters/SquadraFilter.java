package worldcup.dao.filters;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import worldcup.orm.vo.SquadraVO;

public class SquadraFilter implements Specification<SquadraVO>{

	private static final long serialVersionUID = 1L;

	private Optional<String> nomeLike = Optional.empty();
	
	@Override
	public Predicate toPredicate(Root<SquadraVO> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predLst = new ArrayList<>();
		
		if(this.getNomeLike().isPresent()) {
			predLst.add(cb.like(cb.upper(root.get("nome")), "%"+this.getNomeLike().get().toUpperCase()+"%"));
		}
		if(predLst.isEmpty()) {return null;}

		return cb.and(predLst.toArray(new Predicate[]{}));

	}

	public Optional<String> getNomeLike() {
		return nomeLike;
	}

	public void setNomeLike(Optional<String> nomeLike) {
		this.nomeLike = nomeLike;
	}

}
