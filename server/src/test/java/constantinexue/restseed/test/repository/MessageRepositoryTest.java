package constantinexue.restseed.test.repository;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

import constantinexue.restseed.server.common.PagedList;
import constantinexue.restseed.server.entity.MessageEntity;
import constantinexue.restseed.server.entity.UserEntity;
import constantinexue.restseed.server.repository.MessageRepository;
import constantinexue.restseed.server.repository.UserRepository;

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
    
    @Test
    public void fetchAll() throws Exception {
        UserEntity author = createRandomUser();
        userRepository.create(author);
        
        int count = 10;
        List<String> messageIds = Lists.newArrayList();
        for (int i = 0; i < count; i++) {
            String messageText = RandomStringUtils.random(100);
            MessageEntity message = messageRepository.create(author.getId(), messageText);
            messageIds.add(message.getId());
        }
        PagedList<MessageEntity> messages = messageRepository.fetchAll(0, count);
        assertEquals(count, messages.getTotal());
        for (MessageEntity message : messages) {
            assertTrue(messageIds.contains(message.getId()));
        }
    }
}
