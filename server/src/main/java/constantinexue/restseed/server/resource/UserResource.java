package constantinexue.restseed.server.resource;

import java.util.Date;

import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.apache.commons.lang3.StringUtils;

import com.google.inject.Inject;

import constantinexue.restseed.common.exception.UserAlreadyExistException;
import constantinexue.restseed.common.exception.UserNotExistException;
import constantinexue.restseed.common.object.UserObject;
import constantinexue.restseed.server.entity.UserEntity;
import constantinexue.restseed.server.mapper.ObjectMapper;
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
    public UserObject fetchUsers(@QueryParam("id") String userId,
                                 @QueryParam("username") String username) {
        UserEntity user;
        try {
            if (!StringUtils.isEmpty(userId)) {
                // Fetches by user id.
                user = userRepository.fetch(userId);
            }
            else if (!StringUtils.isEmpty(username)) {
                // Fetches by username.
                user = userRepository.fetchByUsername(username);
            }
            else {
                user = null;
            }
        }
        catch (NoResultException e) {
            throw new UserNotExistException();
        }
        if (user == null) {
            throw new UserNotExistException();
        }
        return ObjectMapper.map(user);
    }
    
    @POST
    public UserObject register(@FormParam("username") String username,
                               @FormParam("password") String password) {
        UserEntity user = new UserEntity();
        user.setId(IDUtils.generate())
            .setUsername(username)
            .setPassword(password)
            .setCreatedAt(new Date());
        try {
            userRepository.create(user);
        }
        catch (RollbackException e) {
            throw new UserAlreadyExistException();
        }
        
        //return ObjectMapper.map(user);
        
        return map(user, UserObject.class);
    }
}
