package constantinexue.restseed.client;

import java.lang.reflect.Type;

import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;

import constantinexue.restseed.common.exception.ServiceException;
import constantinexue.restseed.common.object.ErrorObject;
import constantinexue.restseed.common.object.MessageObject;
import constantinexue.restseed.common.object.PagedObject;
import constantinexue.restseed.common.object.RootObject;
import constantinexue.restseed.common.object.UserObject;

public class ServiceClient {
    
    private Gson gson;
    private Client client;
    private String serviceUrl;
    
    private static final TypeToken<PagedObject<MessageObject>> MESSAGES_TYPE_TOKEN = new TypeToken<PagedObject<MessageObject>>() {
    };
    
    public ServiceClient(String host, int port) {
        this(host, port, null, null);
    }
    
    public ServiceClient(String host, int port,
                         String username, String password) {
        serviceUrl = String.format("http://%s:%s", host, port);
        gson = new GsonBuilder().serializeNulls()
                                .registerTypeAdapter(RootObject.class, new RootObjectConverter())
                                .create();
        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
    }
    
    public UserObject register(String username, String password) {
        WebResource resource = client.resource(serviceUrl);
        Form params = new FormBuilder().add("username", username)
                                       .add("password", password)
                                       .create();
        String json = resource.path("/users")
                              .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                              .post(String.class, params);
        return parseObject(UserObject.class, json);
    }
    
    public PagedObject<MessageObject> retrieveMessages(int skip, int take) {
        Form params = new FormBuilder().page(skip, take)
                                       .create();
        WebResource resource = client.resource(serviceUrl);
        String json = resource.path("/messages")
                              .queryParams(params)
                              .get(String.class);
        
        return parseObject(MESSAGES_TYPE_TOKEN.getType(), json);
    }
    
    public MessageObject createMessage(String messageText) {
        return null;
    }
    
    public MessageObject updateMessage(String messageId, String messageText) {
        return null;
    }
    
    public MessageObject deleteMessage(String messageId) {
        return null;
    }
    
    private <T> T parseObject(Type clazz, String json) {
        RootObject root = gson.fromJson(json, RootObject.class);
        if (root.getError() == null) {
            T data = gson.fromJson((String)root.getData(), clazz);
            return data;
        }
        else {
            throw new ServiceException();
        }
    }
    
    private static class FormBuilder {
        
        private Form params;
        
        public FormBuilder() {
            params = new Form();
        }
        
        public FormBuilder add(String name, String value) {
            params.add(name, value);
            return this;
        }
        
        public FormBuilder add(String name, int value) {
            params.add(name, value);
            return this;
        }
        
        public FormBuilder page(int skip, int take) {
            add("skip", skip).add("take", take);
            return this;
        }
        
        public Form create() {
            return params;
        }
    }
    
    private static class RootObjectConverter implements JsonDeserializer<RootObject> {
        
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
}
