package constantinexue.restseed.server.mapper;

import org.joda.time.LocalDateTime;

import constantinexue.restseed.server.common.PagedList;
import constantinexue.restseed.server.entity.MessageEntity;
import constantinexue.restseed.server.entity.UserEntity;
import constantinexue.restseed.server.object.MessageObject;
import constantinexue.restseed.server.object.PagedObject;
import constantinexue.restseed.server.object.UserObject;

public abstract class MapperFactory {
    
    public static UserObject map(UserEntity entity) {
        UserObject object = new UserObject();
        object.setId(entity.getId())
              .setName(entity.getUsername());
        
        return object;
    }
    
    public static MessageObject map(MessageEntity entity) {
        MessageObject object = new MessageObject();
        object.setId(entity.getId())
              .setText(entity.getText())
              .setAuthor(map(entity.getAuthor()))
              .setCreatedAt(new LocalDateTime(entity.getCreatedAt()));
        
        return object;
    }
    
    public static PagedObject<MessageObject> map(PagedList<MessageEntity> entities) {
        PagedObject<MessageObject> objects = new PagedObject<MessageObject>(entities.getTotal());
        for (MessageEntity entity : entities) {
            MessageObject object = map(entity);
            objects.append(object);
        }
        return objects;
    }
}
