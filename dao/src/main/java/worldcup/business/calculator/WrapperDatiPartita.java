package worldcup.business.calculator;

import java.util.Optional;

import worldcup.orm.vo.DatiPartitaVO;

public class WrapperDatiPartita {

	private Optional<DatiPartitaVO> datiPartita = Optional.empty();
	private boolean reverse;
	public boolean isReverse() {
		return reverse;
	}
	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}
	public Optional<DatiPartitaVO> getDatiPartita() {
		return datiPartita;
	}
	public void setDatiPartita(Optional<DatiPartitaVO> datiPartita) {
		this.datiPartita = datiPartita;
	}
}
