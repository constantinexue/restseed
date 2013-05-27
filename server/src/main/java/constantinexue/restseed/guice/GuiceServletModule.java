package constantinexue.restseed.guice;

import java.util.Properties;
import java.util.Set;

import org.reflections.Reflections;

import com.google.inject.name.Names;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import constantinexue.restseed.repository.AbstractRepository;
import constantinexue.restseed.repository.RepositoryInitializer;
import constantinexue.restseed.resource.AbstractResource;
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
        // 把初始化助手类放在第一个初始化，可以避免EntityManager无法注入的问题。
        bind(RepositoryInitializer.class).asEagerSingleton();
        bind(ObjectMapProvider.class).asEagerSingleton();

        bindByAbstractClass(AbstractRepository.class);
        bindByAbstractClass(AbstractResource.class);
        
        serve("/*").with(GuiceContainer.class);
    }
    
    private <T> void bindByAbstractClass(Class<T> abstractClass) {
        Reflections reflections = new Reflections(abstractClass.getPackage().getName());
        Set<Class<? extends T>> subClasses = reflections.getSubTypesOf(abstractClass);
        for (Class<?> subClass : subClasses) {
            bind(subClass).asEagerSingleton();
        }
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
