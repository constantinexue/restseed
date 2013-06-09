package constantinexue.restseed.server.resource.support;

import javax.ws.rs.ext.Provider;

import com.google.inject.Inject;
import com.sun.jersey.core.util.Base64;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

import constantinexue.restseed.server.entity.UserEntity;
import constantinexue.restseed.server.repository.UserRepository;
import constantinexue.restseed.server.util.DebugLogger;

@Provider
public class HTTPBasicAuthFilter implements ContainerRequestFilter {
    
    @Inject
    private UserRepository userRepository;
    
    public HTTPBasicAuthFilter() {
        DebugLogger.logClassCreated(this.getClass());
    }
    
    @Override
    public ContainerRequest filter(ContainerRequest request) {
        try {
            String auth = request.getHeaderValue(ContainerRequest.AUTHORIZATION);
            
            if (auth == null) {
                // throw new WebApplicationException(HttpServletResponse.SC_UNAUTHORIZED);
            }
            else {
                UserEntity authUser = authenticate(auth);
                request.getRequestHeaders().add("author", authUser.getId());
            }
        }
        catch (Exception e) {
        }
        return request;
    }
    
    public UserEntity authenticate(String auth) {
        // Replacing "Basic THE_BASE_64" to "THE_BASE_64" directly
        auth = auth.replaceFirst("[B|b]asic ", "");
        String[] authPair = new String(Base64.base64Decode(auth)).split(":");
        String username = authPair[0];
        String password = authPair[1];
        
        UserEntity userEntity = userRepository.fetch(username, password);
        
        return userEntity;
    }
}
