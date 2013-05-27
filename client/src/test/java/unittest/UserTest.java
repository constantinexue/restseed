package unittest;

import org.junit.Assert;
import org.junit.Test;

import constantinexue.restseed.client.ServiceClient;
import constantinexue.restseed.common.object.UserObject;

public class UserTest extends Assert {
    
    @Test
    public void register() {
        ServiceClient service = new ServiceClient("localhost", 3990);
        
        UserObject user = service.register("username", "password");
        
        assertNotNull(user);
    }
}
