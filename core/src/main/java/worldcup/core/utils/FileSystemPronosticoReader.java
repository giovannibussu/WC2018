package worldcup.core.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import worldcup.core.PronosticoInput;
import worldcup.core.WorldCupProperties;

public class FileSystemPronosticoReader implements PronosticoReader {

	private InputStream is;
	public FileSystemPronosticoReader(String spreadsheetId) throws FileNotFoundException {
		this.is = FileSystemPronosticoReader.class.getResourceAsStream(WorldCupProperties.getInstance().getPronosticiFolder() + "/" + spreadsheetId + ".csv");
	}
	
	public static void main(String[] args) throws Exception {
		FileSystemPronosticoReader reader = new FileSystemPronosticoReader("1QHIq-ZmhoC3mSlBlAmXjhndZb7Ea33SIcZ3W4olHH2I");
		System.out.println(reader.readResults());
	}
	
	public Map<String, PronosticoInput> readResults() {
		BufferedReader breader = new BufferedReader(new InputStreamReader(is));
		return breader.lines().	collect(Collectors.toList()).stream().map(a-> readPronostico(a)).collect(Collectors.toMap(PronosticoInput::getId, Function.identity()));
	}
	
	private PronosticoInput readPronostico(String line) {

		String[] split = line.trim().split(",");
		PronosticoInput input = new PronosticoInput();
		input.setId(split[0].trim());
		input.setHome(Integer.parseInt(split[1].trim()));
		input.setAway(Integer.parseInt(split[2].trim()));
		return input;
		
	}

}
