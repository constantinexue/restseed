package constantinexue.restseed.guice;

import java.util.Properties;

import com.google.inject.name.Names;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import constantinexue.restseed.repository.RepositoryInitializer;
import constantinexue.restseed.resource.UserResource;
import constantinexue.restseed.resource.support.ObjectMapProvider;
import constantinexue.restseed.util.Configuration;
import constantinexue.restseed.util.PropertiesNames;

public class GuiceServletModule extends ServletModule {
    
    public GuiceServletModule() {
    }
    
    @Override
    protected void configureServlets() {
        install(createJpaPersistModule());
        
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
    
    private static JpaPersistModule createJpaPersistModule() {
        Properties properties = new Properties();
        properties.setProperty("javax.persistence.jdbc.driver", "org.h2.Driver");
        properties.setProperty("javax.persistence.jdbc.url", Configuration.getString(PropertiesNames.CONNECTION_URL));
        // properties.setProperty("javax.persistence.jdbc.user", "root");
        // properties.setProperty("javax.persistence.jdbc.password", "");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        return new JpaPersistModule("myFirstJpaUnit").properties(properties);
    }
}
