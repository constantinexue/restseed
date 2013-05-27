package constantinexue.restseed.server.resource.support;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.ArrayUtils;
import org.joda.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import constantinexue.restseed.common.object.ErrorObject;
import constantinexue.restseed.common.object.RootObject;
import constantinexue.restseed.common.object.ValueObject;

@Provider
public class ObjectToJsonProvider implements MessageBodyWriter<ValueObject> {
    
    private final static String ENCODING = "UTF-8";
    
    private final Gson gson;
    
    public ObjectToJsonProvider() {
        gson = createDefaultObjectGson();
    }
    
    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        Class<?>[] interfaces = type.getInterfaces();
        boolean isSupported = type.equals(Object.class) ||
                ArrayUtils.contains(interfaces, List.class) ||
                ArrayUtils.contains(interfaces, ValueObject.class);
        
        return isSupported;
    }
    
    @Override
    public long getSize(ValueObject t, Class<?> type, Type genericType, Annotation[] annotations,
                        MediaType mediaType) {
        return -1;
    }
    
    @Override
    public void writeTo(ValueObject t, Class<?> type, Type genericType, Annotation[] annotations,
                        MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
            throws IOException, WebApplicationException {
        RootObject root = wrapToRootObject(t);
        byte[] bytes = getBytes(root);
        entityStream.write(bytes);
    }
    
    private RootObject wrapToRootObject(ValueObject object) {
        RootObject root;
        if (object instanceof ErrorObject) {
            root = new RootObject().setError((ErrorObject)object);
        }
        else {
            root = new RootObject().setData(object);
        }
        
        return root;
    }
    
    private byte[] getBytes(ValueObject t) throws UnsupportedEncodingException {
        String json;
        try {
            json = gson.toJson(t);
        }
        catch (Exception e) {
            e.printStackTrace();
            json = "";
        }
        return json.getBytes(ENCODING);
    }
    
    private static Gson createDefaultObjectGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateTypeConverter())
               .setPrettyPrinting();
        
        return builder.create();
    }
    
    private static class LocalDateTypeConverter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
        
        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return LocalDate.parse(json.getAsString());
        }
        
        @Override
        public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }
        
    }
}
