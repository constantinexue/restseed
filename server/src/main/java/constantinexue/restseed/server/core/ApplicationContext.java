package constantinexue.restseed.server.core;

import com.google.inject.Inject;

import constantinexue.restseed.server.repository.UserRepository;
import constantinexue.restseed.server.service.TimeService;

public class ApplicationContext {
    
    private UserRepository userRepository;
    
    private TimeService timeService;
    
    public UserRepository getUserRepository() {
        return userRepository;
    }
    
    @Inject
    public ApplicationContext setUserRepository(UserRepository bean) {
        userRepository = bean;
        return this;
    }
    
    public TimeService getTimeService() {
        return timeService;
    }
    
    @Inject
    public ApplicationContext setTimeService(TimeService bean) {
        timeService = bean;
        return this;
    }
}
