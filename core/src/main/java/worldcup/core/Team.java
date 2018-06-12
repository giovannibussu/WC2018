package worldcup.core;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.openspcoop2.utils.resources.FileSystemUtilities;

public class Team extends JsonSerializable {

	private String nome;
	private String id;
	private String bandiera;
	private int fairPlay;
	private String link;

	public int getFairPlay() {
		return fairPlay;
	}
	public void setFairPlay(int fairPlay) {
		this.fairPlay = fairPlay;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(!(obj instanceof Team))
			return false;
		
		return ((Team)obj).getNome().equals(this.nome);

	}
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBandiera() {
		return bandiera;
	}
	public void setBandiera(String bandiera) {
		this.bandiera = bandiera;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	private static Map<String, Team> teams;
	
	public static Map<String, Team> readTeams() {
		if(teams == null) {
			try {
			teams = readTeams("/teams.json");
			} catch(Exception e) {
				teams = new HashMap<>();
			}
		}
		return teams;
	}


	public static Map<String, Team> readTeams(String resource) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		FileSystemUtilities.copy(new FileInputStream(new File(WorldCupProperties.getInstance().getWorldCupExternalFolder(), resource)), baos);
		
		Collection<Team> teamsColl= Deserializer.deserialize(new String(baos.toByteArray()), Team.class);
		
		return teamsColl.stream().collect(Collectors.toMap(Team::getNome, Function.identity()));
		
	}
	
}
