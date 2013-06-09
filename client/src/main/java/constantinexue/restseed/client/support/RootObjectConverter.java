package constantinexue.restseed.client.support;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import constantinexue.restseed.common.object.ErrorObject;
import constantinexue.restseed.common.object.RootObject;

public class RootObjectConverter implements JsonDeserializer<RootObject> {
    
    private static final String ERROR_NODE = "error";
    private static final String DATA_NODE = "data";
    
    @Override
    public RootObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        RootObject root = new RootObject();
        if (json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            if (jsonObject.has(ERROR_NODE)) {
                ErrorObject error = context.deserialize(jsonObject.get(ERROR_NODE), ErrorObject.class);
                root.setError(error);
            }
            else {
                JsonElement dataNode = jsonObject.get(DATA_NODE);
                String data = dataNode.toString();
                root.setData(data);
            }
            return root;
        }
        else {
            throw new RuntimeException();
        }
    }
    
}
