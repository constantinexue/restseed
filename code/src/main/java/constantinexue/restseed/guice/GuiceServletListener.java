package constantinexue.restseed.guice;

import java.util.Properties;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.GuiceServletContextListener;

import constantinexue.restseed.util.Configuration;
import constantinexue.restseed.util.PropertiesNames;

public class GuiceServletListener extends GuiceServletContextListener {
    
    public GuiceServletListener() {
    }
    
    @Override
    protected Injector getInjector() {
        Injector injector = Guice.createInjector(new GuiceServletModule(),
                                                 createJpaPersistModule());
        
        return injector;
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
