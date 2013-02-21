package constantinexue.restseed.mapper;

import constantinexue.restseed.common.PagedList;
import constantinexue.restseed.entity.MessageEntity;
import constantinexue.restseed.entity.UserEntity;
import constantinexue.restseed.object.MessageObject;
import constantinexue.restseed.object.PagedObject;
import constantinexue.restseed.object.UserObject;

public abstract class MapperFactory {
    
    public static UserObject map(UserEntity entity) {
        UserObject object = new UserObject();
        object.id = entity.getId();
        object.name = entity.getUsername();
        
        return object;
    }
    
    public static MessageObject map(MessageEntity entity) {
        MessageObject object = new MessageObject();
        object.id = entity.getId();
        object.text = entity.getText();
        // object.createdAt = new LocalDateTime(entity.getCreatedAt());
        object.author = map(entity.getAuthor());
        
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
