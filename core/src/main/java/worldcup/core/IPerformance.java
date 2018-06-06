package worldcup.core;

public interface IPerformance extends Comparable<IPerformance> {

	public void update(Match d, IPerformance performance);
	public Team getTeam();

}
