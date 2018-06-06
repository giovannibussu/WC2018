package worldcup.core;

import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.openspcoop2.utils.resources.FileSystemUtilities;

public class Stadium extends JsonSerializable{
    private int id;
    private String nome;
    private String citta;
    private String link;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCitta() {
		return citta;
	}
	public void setCitta(String citta) {
		this.citta = citta;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	private static Map<Integer, Stadium> stadiums;
	
	public static Map<Integer, Stadium> getStadiums() {
		if(stadiums == null) {
			try {
			stadiums = readStadiums("/stadi.json");
			} catch(Exception e) {
				stadiums = new HashMap<Integer, Stadium>();
			}
		}
		return stadiums;
	}

	private static Map<Integer, Stadium> readStadiums(String resource) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		FileSystemUtilities.copy(Torneo.class.getResourceAsStream(resource), baos);
		
		Collection<Stadium> teamsColl= Deserializer.deserialize(new String(baos.toByteArray()), Stadium.class);
		
		return teamsColl.stream().collect(Collectors.toMap(Stadium::getId, Function.identity()));
		
	}
	
	

	
}
