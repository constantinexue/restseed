package constantinexue.restseed.test;

import org.junit.Assert;

import com.google.inject.Guice;
import com.google.inject.Injector;

import constantinexue.restseed.guice.GuiceServletModule;
import constantinexue.restseed.util.Configuration;

public abstract class AbstractTest extends Assert {
    
    private static Injector injector;
    
    static{
        try {
            Configuration.loadLog4j("./conf/log4j.xml");
            Configuration.loadProperties("./conf/application.properties");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        injector = Guice.createInjector(new GuiceServletModule());
    }
    
    protected AbstractTest() {
        injector.injectMembers(this);
    }
}
