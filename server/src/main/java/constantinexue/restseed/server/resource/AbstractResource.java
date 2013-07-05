package constantinexue.restseed.server.resource;

import com.google.inject.Inject;

import constantinexue.restseed.server.mapper.MapperService;
import constantinexue.restseed.server.util.DebugLogger;

public abstract class AbstractResource {
    
    @Inject
    private MapperService mapperService;
    
    protected AbstractResource() {
        DebugLogger.logClassCreated(this.getClass());
    }
    
    @SuppressWarnings("unchecked")
    protected <T> T map(Object source, Class<T> clazz) {
        return (T)mapperService.map(source);
    }
}
