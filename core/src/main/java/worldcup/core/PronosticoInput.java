package worldcup.core;

public class PronosticoInput {

	private String id;
	private int home;
	private int away;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getHome() {
		return home;
	}
	public void setHome(int home) {
		this.home = home;
	}
	public int getAway() {
		return away;
	}
	public void setAway(int away) {
		this.away = away;
	}
	
	@Override
	public String toString() {
		return id+","+home+","+away;
	}
}
