package worldcup.dao.filters;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import worldcup.orm.vo.GiocatoreVO;

public class GiocatoreFilter implements Specification<GiocatoreVO>{

	private static final long serialVersionUID = 1L;

	private Optional<String> nome = Optional.empty();
	
	@Override
	public Predicate toPredicate(Root<GiocatoreVO> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predLst = new ArrayList<>();
		
		if(this.nome.isPresent()) {
			predLst.add(cb.equal(root.get("nome"), this.nome.get()));
		}
		if(predLst.isEmpty()) {return null;}

		return cb.and(predLst.toArray(new Predicate[]{}));

	}

	public Optional<String> getNome() {
		return nome;
	}

	public void setNome(Optional<String> nome) {
		this.nome = nome;
	}

}
