package worldcup.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FileSystemPronosticoReader implements PronosticoReader {

	private FileInputStream fis;
	public FileSystemPronosticoReader(String spreadsheetId) throws FileNotFoundException {
		
		File file = new File(WorldCupProperties.getInstance().getPronosticiFolder(), spreadsheetId + ".csv");
		this.fis = new FileInputStream(file);
	}
	
	public static void main(String[] args) throws Exception {
		FileSystemPronosticoReader reader = new FileSystemPronosticoReader("1QHIq-ZmhoC3mSlBlAmXjhndZb7Ea33SIcZ3W4olHH2I");
		System.out.println(reader.readResults());
	}
	
	public Map<String, PronosticoInput> readResults() {
		BufferedReader breader = new BufferedReader(new InputStreamReader(fis));
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
