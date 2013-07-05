package constantinexue.restseed.server.mapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.h2.util.New;
import org.joda.time.LocalDateTime;

import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;

import constantinexue.restseed.common.object.MessageObject;
import constantinexue.restseed.common.object.PagedObject;
import constantinexue.restseed.common.object.UserObject;
import constantinexue.restseed.server.common.PagedList;
import constantinexue.restseed.server.entity.MessageEntity;
import constantinexue.restseed.server.entity.UserEntity;

public class MapperService {
    
    private final static Type messagesType = new TypeToken<PagedObject<MessageObject>>() {

        /**
         * 
         */
        private static final long serialVersionUID = -6993047340639941409L;
    }.getType();
    
    private Map<Type, Mapper<?, ?>> mappers = Maps.newHashMap();
    
    public MapperService() {
        register(new UserMapper()).register(new MessageMapper()).register(new PagedListMapper());
    }
    
    public MapperService register(Mapper<?, ?> mapper) {
        Type genType = mapper.getClass().getGenericSuperclass();
        if (genType instanceof ParameterizedType) {
            Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
            mappers.put(params[0], mapper);
        }
        return this;
    }
    
    @SuppressWarnings("unchecked")
    public <TTarget, TSource> TTarget map(TSource source) {
        Mapper<TSource, TTarget> mapper = (Mapper<TSource, TTarget>)mappers.get(source.getClass());
        
        return mapper.map(source, this);
    }
    
    static class MultiKey {
        
        private Type sourceType;
        private Type targetType;
        
        public MultiKey(Type sourceType, Type targetType) {
            this.sourceType = sourceType;
            this.targetType = targetType;
        }
        
        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(sourceType)
                                        .append(targetType)
                                        .build();
        }
        
        @Override
        public boolean equals(Object obj) {
            return EqualsBuilder.reflectionEquals(this, obj, false);
        }
    }
}

abstract class Mapper<TSource, TTarget> {
    
    public abstract TTarget map(TSource source, MapperService context);
}

class UserMapper extends Mapper<UserEntity, UserObject> {
    
    @Override
    public UserObject map(UserEntity entity, MapperService context) {
        UserObject object = new UserObject();
        object.setId(entity.getId())
              .setName(entity.getUsername());
        
        return object;
    }
}

class MessageMapper extends Mapper<MessageEntity, MessageObject> {
    
    @Override
    public MessageObject map(MessageEntity entity, MapperService context) {
        MessageObject object = new MessageObject();
        object.setId(entity.getId())
              .setText(entity.getText())
              .setAuthor((UserObject)context.map(entity.getAuthor()))
              .setCreatedAt(new LocalDateTime(entity.getCreatedAt()));
        
        return object;
    }
}

class PagedListMapper extends Mapper<PagedList<?>, PagedObject<?>>{

    @Override
    public PagedObject<?> map(PagedList<?> source, MapperService context) {
        PagedObject<Object> objects = new PagedObject<Object>(source.getTotal());
        for (Object entity : source) {
            MessageObject object = context.map(entity);
            objects.append(object);
        }
        
        return objects;
    }
}
