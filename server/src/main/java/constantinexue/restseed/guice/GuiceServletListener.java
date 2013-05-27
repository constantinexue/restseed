package constantinexue.restseed.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class GuiceServletListener extends GuiceServletContextListener {
    
    public GuiceServletListener() {
    }
    
    @Override
    protected Injector getInjector() {
        Injector injector = Guice.createInjector(new GuiceServletModule());
        
        return injector;
    }
}
