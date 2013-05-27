package constantinexue.restseed.test.model;

import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.google.inject.Inject;

import constantinexue.restseed.server.core.ApplicationContext;
import constantinexue.restseed.server.model.UserModel;
import constantinexue.restseed.server.service.TimeService;
import constantinexue.restseed.test.AbstractTest;

public class UserModelTest extends AbstractTest {
    
    @Inject
    private ApplicationContext applicationContext;
    @Mock
    private TimeService timeService;
    
    @Before
    public void mock() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(timeService.getNowDateTime()).thenReturn(LocalDateTime.now());
        applicationContext.setTimeService(timeService);
    }
    
    @Test
    public void create() {
        createRandomUser();
    }
    
    @Test
    public void fetch() {
        UserModel expected = createRandomUser();
        UserModel actual = new UserModel(expected.getId());
        actual.load(applicationContext);
        assertEquals(expected, actual);
    }
    
    @Test
    public void update() {
        UserModel actual = createRandomUser();
        actual.setUsername("changed" + actual.getUsername())
              .save(applicationContext);
        UserModel expected = new UserModel(actual.getId());
        expected.load(applicationContext);
        assertEquals(expected, actual);
    }
    
    @Test
    public void delete() {
        UserModel user = createRandomUser();
        user.destroy(applicationContext);
    }
    
    private UserModel createRandomUser() {
        UserModel user = new UserModel();
        user.setUsername("user" + user.getId())
            .setPassword("Abc123")
            .save(applicationContext);
        
        return user;
    }
    
    private static void assertEquals(UserModel expected, UserModel actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getCreatedTime(), actual.getCreatedTime());
    }
}
