package constantinexue.restseed.server.guice;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.reflections.Reflections;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.name.Names;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.spi.container.ContainerRequestFilter;

import constantinexue.restseed.server.repository.AbstractRepository;
import constantinexue.restseed.server.repository.RepositoryInitializer;
import constantinexue.restseed.server.resource.AbstractResource;
import constantinexue.restseed.server.resource.support.HTTPBasicAuthFilter;
import constantinexue.restseed.server.resource.support.ObjectToJsonProvider;
import constantinexue.restseed.server.util.Configuration;
import constantinexue.restseed.server.util.PropertiesNames;

public class GuiceServletModule extends ServletModule {
    
    public GuiceServletModule() {
    }
    
    @Override
    protected void configureServlets() {
        install(createJpaPersistModule());
        
        Names.bindProperties(binder(), Configuration.getProperties());
        // 把初始化助手类放在第一个初始化，可以避免EntityManager无法注入的问题。
        bindAsEagerSingleton(RepositoryInitializer.class,
                             ObjectToJsonProvider.class);
        
        bindByAbstractClass(AbstractRepository.class);
        bindByAbstractClass(AbstractResource.class);
        
        // Registers ContainerRequestFilters
        Map<String, String> params = new InitParamBuilder().register(HTTPBasicAuthFilter.class)
                                                           .build();
        
        serve("/*").with(GuiceContainer.class, params);
    }
    
    private static class InitParamBuilder {
        
        List<String> containerRequestFilters = Lists.newArrayList();
        
        public InitParamBuilder register(Class<? extends ContainerRequestFilter> clazz) {
            containerRequestFilters.add(clazz.getName());
            return this;
        }
        
        public Map<String, String> build() {
            Map<String, String> params = Maps.newHashMap();
            params.put(ResourceConfig.PROPERTY_CONTAINER_REQUEST_FILTERS,
                       Joiner.on(";").join(containerRequestFilters));
            
            return params;
        }
    }
    
    private void bindAsEagerSingleton(Class<?>... classes) {
        for (Class<?> clazz : classes) {
            bind(clazz).asEagerSingleton();
        }
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
