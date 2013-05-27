package constantinexue.restseed.test;

import org.junit.Assert;
import org.junit.Before;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;

import constantinexue.restseed.guice.GuiceServletModule;
import constantinexue.restseed.util.Configuration;
import constantinexue.restseed.util.PropertiesNames;
import constantinexue.restseed.util.SQLHelper;

public abstract class AbstractTest extends Assert {
    
    private static Injector injector;
    
    @Named(PropertiesNames.CONNECTION_URL)
    @Inject
    private String connectionUrl;
    
    static {
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
    
    @Before
    public void beforeTest() throws Exception {
        SQLHelper.executeScript(connectionUrl, "./conf/clear.sql");
    }
}
