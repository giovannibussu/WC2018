package worldcup.core;

public class PronosticoInput implements Comparable<PronosticoInput> {

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

	@Override
	public int compareTo(PronosticoInput o) {
		try {
			return 	Integer.parseInt(this.id) - Integer.parseInt(o.getId());
		} catch(Exception e) {
			return this.id.compareTo(o.getId());
		}
	}
}
