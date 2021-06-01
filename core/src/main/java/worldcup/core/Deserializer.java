package worldcup.core;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.openspcoop2.utils.UtilsException;
import org.openspcoop2.utils.json.JSONUtils;
import org.openspcoop2.utils.serialization.IDeserializer;
import org.openspcoop2.utils.serialization.IOException;
import org.openspcoop2.utils.serialization.SerializationConfig;
import org.openspcoop2.utils.serialization.SerializationFactory;
import org.openspcoop2.utils.serialization.SerializationFactory.SERIALIZATION_TYPE;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class Deserializer {

	@SuppressWarnings("unchecked")
	public static <T> Collection<T> deserialize(String json, Class<T> t) throws UtilsException, IOException {

		IDeserializer deserializer = SerializationFactory.getDeserializer(SERIALIZATION_TYPE.JSON_JACKSON, new SerializationConfig());

		JSONUtils jsonUtils = new JSONUtils();
		JsonNode asNode = jsonUtils.getAsNode(json);
		ArrayNode array = (ArrayNode) asNode;
		Iterator<JsonNode> iterator = array.iterator();
		
		Collection<T> lst= new ArrayList<>();
		while(iterator.hasNext()) {
			lst.add((T)deserializer.getObject(jsonUtils.toString(iterator.next()), t));
		}
		return lst;
	}

	@SuppressWarnings("unchecked")
	public static <T> T deserializeSingle(String json, Class<T> t) throws UtilsException, IOException {
		
		IDeserializer deserializer = SerializationFactory.getDeserializer(SERIALIZATION_TYPE.JSON_JACKSON, new SerializationConfig());
		return (T) deserializer.getObject(json, t);
	}
	
	public static String getJSON(String resource) throws Exception {
		InputStream is = Deserializer.class.getResourceAsStream(resource);
		if (is== null) {
			throw new Exception("Resource ["+resource+"] null");
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		is.transferTo(baos);
		
		return new String(baos.toByteArray());
	}
}
