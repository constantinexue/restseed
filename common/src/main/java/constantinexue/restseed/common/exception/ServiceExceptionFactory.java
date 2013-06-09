package constantinexue.restseed.common.exception;

import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import com.google.common.collect.Maps;

public abstract class ServiceExceptionFactory {
    
    private static final Map<Integer, ServiceException> exceptionsMapping;
    static {
        exceptionsMapping = Maps.newHashMap();
        Reflections reflections = new Reflections(ServiceException.class.getPackage().getName());
        Set<Class<? extends ServiceException>> subClasses = reflections.getSubTypesOf(ServiceException.class);
        for (Class<?> subClass : subClasses) {
            try {
                ServiceException prototype = (ServiceException)subClass.newInstance();
                exceptionsMapping.put(prototype.getErrorCode(), prototype);
            }
            catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static ServiceException create(int errorCode) {
        ServiceException exception = exceptionsMapping.get(errorCode);
        
        return exception;
    }
}
