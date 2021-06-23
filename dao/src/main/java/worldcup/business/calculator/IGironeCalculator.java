package worldcup.business.calculator;
import java.util.Collection;

import worldcup.orm.vo.PronosticoVO;
import worldcup.orm.vo.SubdivisionVO;

public interface IGironeCalculator {

	public GironeResult getResult(Collection<SubdivisionVO> gironi, PronosticoVO pronostico, RegoleGirone conf); 
}
