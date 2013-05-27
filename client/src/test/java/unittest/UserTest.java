package unittest;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import constantinexue.restseed.client.ServiceClient;
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
    }
    
    @Test
    public void retrieveMessages() {
        ServiceClient service = new ServiceClient("localhost", 3990);
        PagedObject<MessageObject> messages = service.retrieveMessages(0, 10);
        
        for (MessageObject message:messages) {
            Console.out("%s", message.getText());
        }
    }
    
    protected static String createRandomUsername() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
