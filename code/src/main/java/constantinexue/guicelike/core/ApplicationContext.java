package constantinexue.guicelike.core;

import com.google.inject.Inject;

import constantinexue.guicelike.repository.UserRepository;

public class ApplicationContext {
    
    @Inject
    private UserRepository userRepository;
    
    public UserRepository getUserRepository() {
        return userRepository;
    }
}
