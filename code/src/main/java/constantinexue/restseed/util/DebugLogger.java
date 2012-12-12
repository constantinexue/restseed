package constantinexue.restseed.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DebugLogger {
    
    private static final Logger logger = LoggerFactory.getLogger(DebugLogger.class);
    
    public static void logClassCreated(Class<?> clazz) {
        logger.debug("Class created: {}", clazz);
    }
    
    public static void logOutput(String pattern, Object... args) {
        logger.debug(pattern, args);
    }
    
    public static void logException(Exception e){
        logger.error("Exception happend: ", e);
    }
    
    public static void logError(String pattern, Object... args){
        logger.error(pattern, args);
    }
}
