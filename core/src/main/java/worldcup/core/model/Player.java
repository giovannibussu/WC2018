package worldcup.core.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.openspcoop2.utils.resources.FileSystemUtilities;

import worldcup.core.Deserializer;
import worldcup.core.JsonSerializable;
import worldcup.core.WorldCupProperties;

public class Player extends JsonSerializable implements Comparable<Player> {

	private static final String TEMPLATE_LINK = "https://docs.google.com/spreadsheets/d/SPREADSHEET";
	private String nome;
	private String id;
	private String link;
	private String categoria;
	
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
		this.link = TEMPLATE_LINK.replace("SPREADSHEET", this.id);
	}
	
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	private static Map<String, Player> players;
	
	public static Map<String, Player> getPlayers() {
		if(players == null) {
			try {
			players = readPlayers("/players.json");
			} catch(Exception e) {
				System.err.println(e);
				players = new HashMap<String, Player>();
			}
		}
		return players;
	}

	private static Map<String, Player> readPlayers(String resource) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		FileSystemUtilities.copy(new FileInputStream(new File(WorldCupProperties.getInstance().getWorldCupExternalFolder(), resource)), baos);
		
		Collection<Player> playerColl= Deserializer.deserialize(new String(baos.toByteArray()), Player.class);
		
		return playerColl.stream().collect(Collectors.toMap(Player::getId, Function.identity()));
		
	}
	public String getLink() {
		return link;
	}
	@Override
	public int compareTo(Player o) {
		if ( o != null )
    		return this.getNome().compareTo(o.getNome());
		
    	return Integer.MIN_VALUE;
	}

}
