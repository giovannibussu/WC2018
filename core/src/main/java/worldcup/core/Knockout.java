package worldcup.core;

public class Knockout extends AbstractSubTorneo {

	public Knockout(String name, TYPE type) {
		super(name, 2, 1, type);
	}

	@Override
	protected IPerformance newIPerformance(Team team) {
		return new KnockoutPerformance(team);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.getType()).append("\n");
		if(this.getMatches().size() > 0) {
			for(Match match: this.getMatches()) {
				sb.append(match.toString()).append("\n");
			}
		}
		return sb.toString();
	}

	@Override
	public boolean isDrawable() {
		return false;
	}

}
