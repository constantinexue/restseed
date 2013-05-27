package constantinexue.restseed.server.resource.support;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.google.inject.Singleton;

import constantinexue.restseed.common.exception.ServiceException;
import constantinexue.restseed.common.object.ErrorObject;

@Singleton
@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<ServiceException> {
    
    public ApplicationExceptionMapper() {
    }
    
    @Override
    public Response toResponse(ServiceException exception) {
        ErrorObject error = new ErrorObject();

        error.setCode(500)
             .setMessage("Some unknown error happened in server.");
        
        return Response.ok(error).build();
    }
}
