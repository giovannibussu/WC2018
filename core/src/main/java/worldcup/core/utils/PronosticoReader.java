package worldcup.core.utils;

import java.util.Map;

import worldcup.core.PronosticoInput;

public interface PronosticoReader {

	public Map<String, PronosticoInput> readResults();
}
