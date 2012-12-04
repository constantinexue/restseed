package constantinexue.restseed.test.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.inject.Inject;

import constantinexue.restseed.core.ApplicationContext;
import constantinexue.restseed.model.UserModel;
import constantinexue.restseed.repository.UserRepository;
import constantinexue.restseed.test.AbstractTest;

public class UserModelTest extends AbstractTest {
    
    @Inject
    private ApplicationContext applicationContext;
    
    @Before
    public void mock() {
        applicationContext = Mockito.mock(ApplicationContext.class);
        Mockito.when(applicationContext.getUserRepository()).thenReturn(Mockito.mock(UserRepository.class));
    }
    
    @Test
    public void createUser() {
        UserModel user = new UserModel();
        user.setName("xk")
            .setPassword("Abc123")
            .save(applicationContext);
    }
}
