package worldcup.core;

import java.util.HashMap;
import java.util.Map;

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

	public Map<String, Team> getPosizioni() {
		Map<String, Team> posizioni = new HashMap<String, Team>();
		Match next = this.getMatches().iterator().next();
		posizioni.put(this.getName() + "-0", next.getHome());
		posizioni.put(this.getName() + "-1", next.getAway());
		return posizioni;
	}

	@Override
	public boolean isDrawable() {
		return false;
	}

}
