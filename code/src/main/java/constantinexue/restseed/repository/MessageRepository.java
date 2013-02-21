package constantinexue.restseed.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

import constantinexue.restseed.common.PagedList;
import constantinexue.restseed.entity.MessageEntity;
import constantinexue.restseed.entity.UserEntity;
import constantinexue.restseed.repository.query.QueryBuilderContext;
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
    
    @Transactional
    public PagedList<MessageEntity> fetchAll(int skip, int take) {
        QueryBuilderContext<MessageEntity> context = createQueryBuilderContext(MessageEntity.class);
        
        CriteriaQuery<MessageEntity> cq = context.getQuery();
        List<MessageEntity> entities = fetchResultList(cq, skip, take);
        
        Long total = fetchSingleResult(context.getCountQuery());
        
        return new PagedList<MessageEntity>(entities, total);
    }
}
