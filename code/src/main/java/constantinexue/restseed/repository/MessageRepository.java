package constantinexue.restseed.repository;

import java.util.Date;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

import constantinexue.restseed.entity.MessageEntity;
import constantinexue.restseed.entity.UserEntity;
import constantinexue.restseed.util.IDUtils;

@Singleton
public class MessageRepository extends AbstractRepository<MessageEntity> {
    
    @Inject
    private UserRepository userRepository;
    
    public MessageRepository() {
        super(MessageEntity.class);
    }
    
    @Transactional
    public MessageEntity create(String authorId, String messageText) {
        UserEntity author = userRepository.fetch(authorId);
        MessageEntity message = new MessageEntity();
        message.setId(IDUtils.generate())
               .setText(messageText)
               .setCreatedAt(new Date())
               .setAuthor(author);
        
        create(message);
        message = fetch(message.getId());
        
        return message;
    }
}
