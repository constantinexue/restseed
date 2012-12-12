package constantinexue.restseed.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.google.inject.Singleton;

import constantinexue.restseed.model.AllUsersModel;
import constantinexue.restseed.object.UserObject;

@Singleton
@Path("/users")
public class UserResource extends AbstractResource {
    
    public UserResource() {
        super();
    }
    
    @GET
    public List<UserObject> allUsers() {
        AllUsersModel allUsers = new AllUsersModel();
        allUsers.load(getApplicationContext().getUserRepository());
        
        return mapList(allUsers, UserObject.class);
    }
}
