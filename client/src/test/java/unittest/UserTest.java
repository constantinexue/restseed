package unittest;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import constantinexue.restseed.client.ServiceClient;
import constantinexue.restseed.common.exception.UserAlreadyExistException;
import constantinexue.restseed.common.exception.UserNotExistException;
import constantinexue.restseed.common.object.MessageObject;
import constantinexue.restseed.common.object.PagedObject;
import constantinexue.restseed.common.object.UserObject;
import constantinexue.restseed.common.util.Console;

public class UserTest extends Assert {
    
    @Test
    public void register() {
        ServiceClient service = new ServiceClient("localhost", 3990);
        String username = createRandomUsername();
        UserObject user = service.register(username, "Abc123");
        
        assertNotNull(user);
        
        try {
            service.fetchUserByUsername("xxx");
            fail();
        }
        catch (UserNotExistException e) {
        }
        
        try {
            service.register(username, "Abc123");
            fail();
        }
        catch (UserAlreadyExistException e) {
        }
    }
    
    @Test
    public void retrieveMessages() {
        ServiceClient service = new ServiceClient("localhost", 3990);
        String username = createRandomUsername();
        service.register(username, "Abc123");
        service = new ServiceClient("localhost", 3990, username, "Abc123");
        service.createMessage("Hello world");
        PagedObject<MessageObject> messages = service.retrieveMessages(0, 10);
        
        for (MessageObject message : messages) {
            Console.out("%s", message.getText());
        }
    }
    
    protected static String createRandomUsername() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
