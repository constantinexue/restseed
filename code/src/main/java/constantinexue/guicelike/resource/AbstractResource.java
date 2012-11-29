package constantinexue.guicelike.resource;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Injector;

import constantinexue.guicelike.core.ApplicationContext;

public abstract class AbstractResource {
    
    private Injector injector;
    
    private ModelMapper modelMapper;
    
    protected AbstractResource() {
    }
    
    @Inject
    protected void setInjector(Injector bean) {
        injector = bean;
        // Provider<?> guiceProvider = GuiceIntegration.fromGuice(injector);
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                   .enableFieldMatching(true)
                   .setFieldAccessLevel(AccessLevel.PRIVATE);
        // modelMapper.getConfiguration().setProvider((Provider<?>)guiceProvider);
    }
    
    protected ApplicationContext getApplicationContext() {
        return injector.getInstance(ApplicationContext.class);
    }
    
    protected <T> T map(Object model, Class<T> clazz) {
        return modelMapper.map(model, clazz);
    }
    
    protected <T, K> List<T> mapList(Iterable<K> models, Class<T> clazz) {
        List<T> objects = Lists.newArrayList();
        for (K model : models) {
            T object = map(model, clazz);
            objects.add(object);
        }
        return objects;
    }
    
}
