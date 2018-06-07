package worldcup.core;

import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.openspcoop2.utils.resources.FileSystemUtilities;

public class Player extends JsonSerializable {

	private String nome;
	private String id;
	
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
	
	
	private static Map<String, Player> players;
	
	public static Map<String, Player> getPlayers() {
		if(players == null) {
			try {
			players = readPlayers("/players.json");
			} catch(Exception e) {
				players = new HashMap<String, Player>();
			}
		}
		return players;
	}

	private static Map<String, Player> readPlayers(String resource) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		FileSystemUtilities.copy(Player.class.getResourceAsStream(resource), baos);
		
		Collection<Player> playerColl= Deserializer.deserialize(new String(baos.toByteArray()), Player.class);
		
		return playerColl.stream().collect(Collectors.toMap(Player::getId, Function.identity()));
		
	}

}
