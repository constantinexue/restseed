package constantinexue.restseed.server.repository;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.persist.PersistService;

import constantinexue.restseed.server.util.PropertiesNames;
import constantinexue.restseed.server.util.SQLHelper;

@Singleton
public class RepositoryInitializer {
    
    @Named(PropertiesNames.CONNECTION_URL)
    @Inject
    private String connectionUrl;
    
    @Inject
    public RepositoryInitializer(PersistService service) {
        service.start();
    }
    
    @Inject
    public void initialize() throws Exception {
        // Creates database tables by executing scripts.
        SQLHelper.executeScript(connectionUrl, "./conf/create.sql");
    }
}
