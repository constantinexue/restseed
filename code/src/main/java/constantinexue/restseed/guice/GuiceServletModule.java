package constantinexue.restseed.guice;

import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import constantinexue.restseed.repository.RepositoryInitializer;
import constantinexue.restseed.resource.UserResource;
import constantinexue.restseed.resource.support.ObjectMapProvider;
import constantinexue.restseed.util.Configuration;

public class GuiceServletModule extends ServletModule {
    
    public GuiceServletModule() {
    }
    
    @Override
    protected void configureServlets() {
        Names.bindProperties(binder(), Configuration.getProperties());
        // bind(GPUEventBus.class).asEagerSingleton();
        // bind(CPUEventBus.class).asEagerSingleton();
        // bind(FileManager.class).to(GuavaFileManager.class);
        // bind(Mediator.class).asEagerSingleton();
        // bind(ApplicationExceptionMapper.class);
        // bind(Recorder.class).to(SimpleRecorder.class).asEagerSingleton();
        bind(ObjectMapProvider.class).asEagerSingleton();
        bind(UserResource.class).asEagerSingleton();
        bind(RepositoryInitializer.class).asEagerSingleton();
        
        serve("/*").with(GuiceContainer.class);
    }
}
