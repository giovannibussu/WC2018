package worldcup.core.utils;

import org.openspcoop2.utils.UtilsException;
import org.openspcoop2.utils.json.JSONUtils;
import org.openspcoop2.utils.serialization.IOException;
import org.openspcoop2.utils.serialization.ISerializer;
import org.openspcoop2.utils.serialization.SerializationConfig;
import org.openspcoop2.utils.serialization.SerializationFactory;
import org.openspcoop2.utils.serialization.SerializationFactory.SERIALIZATION_TYPE;

import com.fasterxml.jackson.databind.JsonNode;

public class JsonSerializable {

	public JsonNode serialize() throws UtilsException, IOException {
		return new JSONUtils().getAsNode(this.serializeToString());
	}

	public String serializeToString() throws UtilsException, IOException {
		return this.serializeToString(new SerializationConfig());
	}

	public String serializeToString(SerializationConfig config) throws UtilsException, IOException {
		ISerializer serializer = SerializationFactory.getSerializer(SERIALIZATION_TYPE.JSON_JACKSON, config);
		return serializer.getObject(this);
	}

	public static String serializeToString(Object obj) throws UtilsException, IOException {
		return JsonSerializable.serializeToString(obj, new SerializationConfig());
	}

	public static String serializeToString(Object obj, SerializationConfig config) throws UtilsException, IOException {
		ISerializer serializer = SerializationFactory.getSerializer(SERIALIZATION_TYPE.JSON_JACKSON, config);
		return serializer.getObject(obj);
	}
	
}
