package constantinexue.restseed.test.repository;

import java.util.Date;

import org.junit.Test;

import com.google.inject.Inject;

import constantinexue.restseed.entity.UserEntity;
import constantinexue.restseed.repository.UserRepository;
import constantinexue.restseed.test.AbstractTest;
import constantinexue.restseed.util.IDUtils;

public class UserRepositoryTest extends AbstractTest {
    
    @Inject
    private UserRepository userRepository;
    
    @Test
    public void create() {
        UserEntity expected = createRandomUser();
        userRepository.create(expected);
        UserEntity actual = userRepository.fetch(expected.getId());
        assertEquals(expected, actual);
    }
    
    @Test
    public void createDuplicate() {
        UserEntity user = createRandomUser();
        userRepository.create(user);
        // 惊奇！为什么这里没有抛出EntityExistsException
        userRepository.create(user);
    }
    
    @Test
    public void fetch() {
        UserEntity expected = createRandomUser();
        userRepository.create(expected);
        UserEntity actual = userRepository.fetch(expected.getId());
        assertEquals(expected, actual);
    }
    
    @Test
    public void fetchNotExisted() {
        UserEntity user = userRepository.fetch("abcd");
        assertNull(user);
    }
    
    @Test
    public void update() {
        UserEntity expected = createRandomUser();
        userRepository.create(expected);
        expected.setUsername("changed");
        UserEntity actual = userRepository.update(expected);
        assertEquals(expected, actual);
    }
    
    @Test
    public void delete() {
        UserEntity user = createRandomUser();
        userRepository.create(user);
        userRepository.delete(user);
        UserEntity expected = userRepository.fetch(user.getId());
        assertNull(expected);
    }
    
    private static UserEntity createRandomUser() {
        String id = IDUtils.generate();
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setUsername("user" + id);
        user.setPassword("Abc123");
        user.setCreatedAt(new Date());
        
        return user;
    }
    
    private static void assertEquals(UserEntity expected, UserEntity actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getCreatedAt(), actual.getCreatedAt());
    }
}
