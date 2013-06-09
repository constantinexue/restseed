package constantinexue.restseed.server.resource.support;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.google.inject.Singleton;

import constantinexue.restseed.common.exception.ServiceException;
import constantinexue.restseed.common.object.ErrorObject;

@Singleton
@Provider
public class ServiceExceptionMapper implements ExceptionMapper<ServiceException> {
    
    public ServiceExceptionMapper() {
    }
    
    @Override
    public Response toResponse(ServiceException exception) {
        ErrorObject error = new ErrorObject(exception.getErrorCode(), exception.getClass().getSimpleName());
        
        int statusCode = error.getCode() / 1000;
        
        return Response.status(200)
                       .entity(error)
                       .build();
    }
}
