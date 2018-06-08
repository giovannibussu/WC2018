package worldcup.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openspcoop2.utils.json.JSONUtils;

import com.fasterxml.jackson.databind.JsonNode;

public class GoogleApiPronosticoReader implements PronosticoReader {

	private class GoogleApiReaderInput {
		public GoogleApiReaderInput(String[] split) {
			id = split[0];
			cellHome=split[1];
			cellAway =split[2];
			}
		String id;
		String cellHome;
		String cellAway;
	}
	
	private static String googleApiReaderInputFile= "/googleApiInputFile.csv";
	private static String urlTemplate = "https://sheets.googleapis.com/v4/spreadsheets/SPREADSHEET/values/Tournament!CELL?key=AIzaSyA-vFlRYfwtSjjOAmnuOmpMAO7ht1ayAok";
	private String url;
	private List<GoogleApiReaderInput> lst;
	
	public GoogleApiPronosticoReader(String spreadsheetId) {
		this.url = urlTemplate.replace("SPREADSHEET", spreadsheetId);
		InputStream isGoogleApi = Torneo.class.getResourceAsStream(googleApiReaderInputFile);
		BufferedReader breader = new BufferedReader(new InputStreamReader(isGoogleApi));
		
		this.lst = breader.lines().collect(Collectors.toList()).stream().map(a-> new GoogleApiReaderInput(a.split(","))).collect(Collectors.toList());
		
	}
	
	public static void main(String[] args) throws Exception {
		GoogleApiPronosticoReader reader = new GoogleApiPronosticoReader("1QHIq-ZmhoC3mSlBlAmXjhndZb7Ea33SIcZ3W4olHH2I");
		System.out.println(reader.readResults());
	}
	
	public List<PronosticoInput> readResults() {
		List<PronosticoInput> inputLst = new ArrayList<>();
		try {
			for(GoogleApiReaderInput reader: this.lst) {
				System.out.println("Lettura risultato gara["+reader.id+"]");
				int home = readResult(reader.cellHome);
				int away = readResult(reader.cellAway);
				
				PronosticoInput input = new PronosticoInput();
				input.setId(reader.id);
				input.setHome(home);
				input.setAway(away);
				inputLst.add(input);
				System.out.println("Lettura risultato gara["+reader.id+"] completato");
				
			}
		} catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return inputLst;
	}
	
	public int readResult(String cell) throws Exception {

		int responseCode = 999;
		HttpURLConnection con = null;
		System.out.println("Lettura cella["+cell+"]");
		URL url = new URL(this.url.replace("CELL", cell));

		while(responseCode > 300) {
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			responseCode = con.getResponseCode();
			
			if(responseCode == 404) {
				throw new Exception("GoogleDocs non trovato: ["+url+"]");
			}			
			if(responseCode > 300) {
				System.out.println("ResponseCode: " +responseCode);
				Thread.sleep(30000);
				System.out.println("Riprovo");
			}
		}
		System.out.println("Lettura cella["+cell+"] completata");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		in.close();
	
		
		con.disconnect();
		System.out.println("Lettura cella["+cell+"] completata");

		JSONUtils utils = new JSONUtils();
		JsonNode asNode = utils.getAsNode(content.toString());

		return Integer.parseInt(asNode.get("values").get(0).get(0).asText());
	}

}
