package constantinexue.restseed.server.resource.support;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.google.inject.Singleton;

@Singleton
@Provider
public class ServiceExceptionMapper implements ExceptionMapper<RuntimeException> {
    
    public ServiceExceptionMapper() {
    }
    
    @Override
    public Response toResponse(RuntimeException exception) {
//        ErrorObject error = new ErrorObject(exception.getErrorCode(), exception.getClass().getSimpleName());
//
//        int statusCode = error.getCode() / 1000;
//        // Cannot return 404 error directly, because it will cause client throw exceptions before resolving json.
//        statusCode = 200;
        
//        return Response.status(statusCode)
//                       .entity(error)
//                       .build();
        return Response.status(500).build();
    }
}
