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

public class UnitTest extends Assert {
    
    private static final String DEFAULT_PASSWORD = "Abc123";
    
    @Test
    public void testUser() {
        ServiceClient service = createServiceClient();
        String username = createRandomUsername();
        UserObject user = service.register(username, DEFAULT_PASSWORD);
        
        assertNotNull(user);
        
        try {
            service.fetchUserByUsername("xxx");
            fail();
        }
        catch (UserNotExistException e) {
        }
        
        try {
            service.register(username, DEFAULT_PASSWORD);
            fail();
        }
        catch (UserAlreadyExistException e) {
        }
    }
    
    @Test
    public void testMessages() {
        ServiceClient service = createServiceClient();
        String username = createRandomUsername();
        UserObject author = service.register(username, "Abc123");
        service = createServiceClient(username);
        MessageObject expected = service.createMessage("Hello world");
        PagedObject<MessageObject> messages = service.retrieveMessagesByAuthor(author.getId(), 0, 10);
        assertEquals(1, messages.getCount());
        assertEquals(1, messages.getTotal());
        MessageObject actual = messages.get(0);
        
        assertEquals(expected, actual);
    }
    
    protected static String createRandomUsername() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    
    protected static ServiceClient createServiceClient() {
        ServiceClient service = new ServiceClient("localhost", 3990);
        return service;
    }
    
    protected static ServiceClient createServiceClient(String username) {
        ServiceClient service = new ServiceClient("localhost", 3990, username, DEFAULT_PASSWORD);
        return service;
    }
    
    protected static void assertEquals(MessageObject expected, MessageObject actual){
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getText(), actual.getText());
        assertEquals(expected.getCreatedAt().toString(), actual.getCreatedAt().toString());
    }
}
