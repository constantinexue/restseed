package constantinexue.restseed.test.repository;

import java.util.Date;

import constantinexue.restseed.entity.UserEntity;
import constantinexue.restseed.test.AbstractTest;
import constantinexue.restseed.util.IDUtils;

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
