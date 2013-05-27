package constantinexue.restseed.test.repository;

import java.util.Date;

import constantinexue.restseed.server.entity.UserEntity;
import constantinexue.restseed.server.util.IDUtils;
import constantinexue.restseed.test.AbstractTest;

public abstract class AbstractRepositoryTest extends AbstractTest {
    
    protected static UserEntity createRandomUser() {
        String id = IDUtils.generate();
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setUsername("user" + id);
        user.setPassword("Abc123");
        user.setCreatedAt(new Date());
        
        return user;
    }
}
