package worldcup.core;

import worldcup.core.model.Match;
import worldcup.core.model.Team;

public interface IPerformance extends Comparable<IPerformance> {

	public void update(Match d, IPerformance performance);
	public Team getTeam();

}
