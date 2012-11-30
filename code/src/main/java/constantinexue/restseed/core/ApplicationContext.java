package constantinexue.restseed.core;

import com.google.inject.Inject;

import constantinexue.restseed.repository.UserRepository;

public class ApplicationContext {
    
    @Inject
    private UserRepository userRepository;
    
    public UserRepository getUserRepository() {
        return userRepository;
    }
}
