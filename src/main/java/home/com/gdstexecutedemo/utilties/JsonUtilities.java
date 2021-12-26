package home.com.gdstexecutedemo.utilties;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtilities<T> {

	public static String SerializeToJson(Object claim) throws Exception
	{
		final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper.writeValueAsString(claim);
	}
	
	public Object CreateObjFromJson(String request, Class<? extends Object> class1) throws Exception
	{
		final ObjectMapper objectMapper = GetJsonMapper();
		T tObj = (T) class1.getDeclaredConstructor().newInstance();
		JavaType type = objectMapper.getTypeFactory().constructType(tObj.getClass());

		//SimpleModule module = new SimpleModule();
		//module.addDeserializer(LocalDateTime.class, new TimeStampDeserializer());
		//objectMapper.registerModule(module);
		
		tObj = objectMapper.readValue(request, type);    
	    return tObj;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
	public List<T> CreateListFromJson(String request, Class<T> clazz) throws Exception
	{
		final ObjectMapper objectMapper = GetJsonMapper();
		T tObj = clazz.getDeclaredConstructor().newInstance();
		JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, tObj.getClass());
	    List<T> tmpList = objectMapper.readValue(request, type);    
	    return tmpList;
	}	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
	@SuppressWarnings("deprecation")
	public static ObjectMapper GetJsonMapper()
	{
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    objectMapper.registerModule(new JavaTimeModule());
	    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	    return objectMapper; 
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
	@SuppressWarnings("deprecation")
	public static ObjectMapper GetYamlMapper()
	{
		ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
		objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    objectMapper.registerModule(new JavaTimeModule());
	    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	    return objectMapper; 
	}	
	
}