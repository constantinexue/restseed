package constantinexue.restseed.core;

import com.google.inject.Inject;

import constantinexue.restseed.repository.UserRepository;
import constantinexue.restseed.service.TimeService;

public class ApplicationContext {
    
    @Inject
    private UserRepository userRepository;
    
    @Inject
    private TimeService timeService;
    
    public UserRepository userRepository() {
        return userRepository;
    }
    
    public TimeService timeService() {
        return timeService;
    }
}
