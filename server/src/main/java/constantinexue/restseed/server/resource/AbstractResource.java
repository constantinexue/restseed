package constantinexue.restseed.server.resource;

import constantinexue.restseed.server.util.DebugLogger;

public abstract class AbstractResource {
    
    protected AbstractResource() {
        DebugLogger.logClassCreated(this.getClass());
    }
}
