package worldcup.business.calculator;

public class RisultatoPronostico {

	private int risultatiEsatti=0;
	private int risultati1x2=0;
	public int getRisultatiEsatti() {
		return risultatiEsatti;
	}
	public void setRisultatiEsatti(int risultatiEsatti) {
		this.risultatiEsatti = risultatiEsatti;
	}
	public int getRisultati1x2() {
		return risultati1x2;
	}
	public void setRisultati1x2(int risultati1x2) {
		this.risultati1x2 = risultati1x2;
	}

	public void addRisultati1x2() {
		this.risultati1x2++;
	}

	public void addRisultatiEsatti() {
		this.risultatiEsatti++;
	}
}
