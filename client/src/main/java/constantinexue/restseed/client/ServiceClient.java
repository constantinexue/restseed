package constantinexue.restseed.client;

import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;

import constantinexue.restseed.common.exception.ServiceException;
import constantinexue.restseed.common.object.MessageListObject;
import constantinexue.restseed.common.object.MessageObject;
import constantinexue.restseed.common.object.RootObject;
import constantinexue.restseed.common.object.UserObject;

public class ServiceClient {
    
    private Gson gson;
    private Client client;
    private String serviceUrl;
    
    public ServiceClient(String host, int port) {
        this(host, port, null, null);
    }
    
    public ServiceClient(String host, int port,
                         String username, String password) {
        serviceUrl = String.format("http://%s:%s", host, port);
        gson = new GsonBuilder().create();
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
                              .accept(MediaType.APPLICATION_JSON_TYPE)
                              .post(String.class, params);
        return parseObject(UserObject.class, json);
    }
    
    public MessageListObject retrieveMessages(int skip, int take) {
        Form params = new FormBuilder().page(skip, take)
                                       .create();
        WebResource resource = client.resource(serviceUrl);
        String json = resource.path("/users")
                              .queryParams(params)
                              .accept(MediaType.APPLICATION_JSON_TYPE)
                              .get(String.class);
        
        return parseObject(MessageListObject.class, json);
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
    
    @SuppressWarnings("unchecked")
    private <T> T parseObject(Class<T> clazz, String json) {
        RootObject<T> root = new RootObject<T>();
        root = gson.fromJson(json, root.getClass());
        if (root.getError() == null) {
            return root.getData();
        }
        else {
            throw new ServiceException();
        }
    }
    
    private class FormBuilder {
        
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
}
