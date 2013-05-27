package constantinexue.restseed.server.resource;

import java.util.Date;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.google.inject.Inject;

import constantinexue.restseed.common.object.UserObject;
import constantinexue.restseed.server.entity.UserEntity;
import constantinexue.restseed.server.mapper.MapperFactory;
import constantinexue.restseed.server.model.AllUsersModel;
import constantinexue.restseed.server.repository.UserRepository;
import constantinexue.restseed.server.util.IDUtils;

@Path("/users")
public class UserResource extends AbstractResource {
    
    @Inject
    private UserRepository userRepository;
    
    public UserResource() {
        super();
    }
    
    @GET
    public List<UserObject> allUsers() {
        AllUsersModel allUsers = new AllUsersModel();
        allUsers.load(getApplicationContext().getUserRepository());
        
        return mapList(allUsers, UserObject.class);
    }
    
    @POST
    public UserObject register(@FormParam("username") String username,
                               @FormParam("password") String password) {
        UserEntity user = new UserEntity();
        user.setId(IDUtils.generate())
            .setUsername(username)
            .setPassword(password)
            .setCreatedAt(new Date());
        
        userRepository.create(user);
        
        return MapperFactory.map(user);
    }
}
