package constantinexue.restseed.test.repository;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import com.google.inject.Inject;

import constantinexue.restseed.entity.MessageEntity;
import constantinexue.restseed.entity.UserEntity;
import constantinexue.restseed.repository.MessageRepository;
import constantinexue.restseed.repository.UserRepository;

public class MessageRepositoryTest extends AbstractRepositoryTest {
    
    @Inject
    private MessageRepository messageRepository;
    @Inject
    private UserRepository userRepository;
    
    @Test
    public void create() throws Exception {
        UserEntity author = createRandomUser();
        userRepository.create(author);
        
        String messageText = RandomStringUtils.random(100);
        MessageEntity message = messageRepository.create(author.getId(), messageText);
        
        assertEquals(messageText, message.getText());
    }
}
