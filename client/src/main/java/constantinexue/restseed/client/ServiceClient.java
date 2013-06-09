package constantinexue.restseed.client;

import java.lang.reflect.Type;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.representation.Form;

import constantinexue.restseed.client.support.RootObjectConverter;
import constantinexue.restseed.common.exception.ServiceException;
import constantinexue.restseed.common.object.MessageObject;
import constantinexue.restseed.common.object.PagedObject;
import constantinexue.restseed.common.object.RootObject;
import constantinexue.restseed.common.object.UserObject;
import constantinexue.restseed.common.util.LocalDateTimeConverter;

public class ServiceClient {
    
    private static final Type MESSAGES_TYPE = new TypeToken<PagedObject<MessageObject>>() {
    }.getType();
    
    private Gson gson;
    private Client client;
    private String serviceUrl;
    
    public ServiceClient(String host, int port) {
        this(host, port, null, null);
    }
    
    public ServiceClient(String host, int port,
                         String username, String password) {
        serviceUrl = String.format("http://%s:%s", host, port);
        gson = new GsonBuilder().serializeNulls()
                                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeConverter())
                                .registerTypeAdapter(RootObject.class, new RootObjectConverter())
                                .create();
        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            // No Authentication
        }
        else {
            client.addFilter(new HTTPBasicAuthFilter(username, password));
        }
    }
    
    public UserObject register(String username, String password) {
        Form params = new FormBuilder().add("username", username)
                                       .add("password", password)
                                       .create();
        String json = resource().path("/users")
                                .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                                .post(String.class, params);
        return parseObject(UserObject.class, json);
    }
    
    public PagedObject<MessageObject> retrieveMessages(int skip, int take) {
        Form params = new FormBuilder().page(skip, take)
                                       .create();
        String json = resource().path("/messages")
                                .queryParams(params)
                                .get(String.class);
        
        return parseObject(MESSAGES_TYPE, json);
    }
    
    public MessageObject createMessage(String messageText) {
        Form params = new FormBuilder().add("text", messageText)
                                       .create();
        String json = resource().path("/messages")
                                .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                                .post(String.class, params);
        return parseObject(MessageObject.class, json);
    }
    
    public MessageObject updateMessage(String messageId, String messageText) {
        Form params = new FormBuilder().add("text", messageText)
                                       .create();
        String json = resource().path("/messages/" + messageId)
                                .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                                .put(String.class, params);
        return parseObject(MessageObject.class, json);
    }
    
    public MessageObject deleteMessage(String messageId) {
        String json = resource().path("/messages/" + messageId)
                                .delete(String.class);
        
        return parseObject(MessageObject.class, json);
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
    
    private WebResource resource() {
        return client.resource(serviceUrl);
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
}
