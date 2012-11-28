package constantinexue.guicelike.guice;

import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import constantinexue.guicelike.repository.RepositoryInitializer;
import constantinexue.guicelike.resource.UserResource;
import constantinexue.guicelike.util.Configuration;

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
        // bind(ValueObjectProvider.class).asEagerSingleton();
        bind(UserResource.class).asEagerSingleton();
        bind(RepositoryInitializer.class).asEagerSingleton();
        
        serve("/*").with(GuiceContainer.class);
    }
}
