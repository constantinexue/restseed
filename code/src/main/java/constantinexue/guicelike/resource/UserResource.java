package constantinexue.guicelike.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.google.inject.Singleton;

import constantinexue.guicelike.model.AllUsersModel;
import constantinexue.guicelike.object.UserObject;

@Singleton
@Path("/users")
public class UserResource extends AbstractResource {

    @GET
    public List<UserObject> allUsers(){
        AllUsersModel allUsers = new AllUsersModel();
        allUsers.load(getApplicationContext().getUserRepository());
        
        return mapList(allUsers, UserObject.class);
    }
}
