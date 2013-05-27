package constantinexue.restseed.server.model;

import org.joda.time.LocalDateTime;

import constantinexue.restseed.server.core.ApplicationContext;
import constantinexue.restseed.server.entity.UserEntity;
import constantinexue.restseed.server.util.IDUtils;

public class UserModel {
    
    private boolean isNew;
    private String id;
    private UserEntity entity;
    
    public UserModel() {
        this.id = IDUtils.generate();
        this.entity = new UserEntity();
        this.entity.setId(this.id);
        this.isNew = true;
    }
    
    public UserModel(String id) {
        this.id = id;
        this.isNew = false;
    }
    
    public UserModel(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.entity = userEntity;
        this.isNew = false;
    }
    
    public String getId() {
        return id;
    }
    
    public String getUsername() {
        return entity.getUsername();
    }
    
    public UserModel setUsername(String name) {
        entity.setUsername(name);
        return this;
    }
    
    public UserModel setPassword(String plainPassword) {
        String password = Integer.toString(plainPassword.hashCode());
        entity.setPassword(password);
        return this;
    }
    
    public LocalDateTime getCreatedTime() {
        return new LocalDateTime(entity.getCreatedAt());
    }
    
    public UserModel save(ApplicationContext context) {
        if (isNew) {
            // 设置创建时间
            entity.setCreatedAt(context.getTimeService().getNowDateTime().toDate());
            context.getUserRepository().create(entity);
            entity = context.getUserRepository().fetch(id);
            isNew = false;
        }
        else {
            entity = context.getUserRepository().update(entity);
        }
        return this;
    }
    
    public UserModel load(ApplicationContext context) {
        entity = context.getUserRepository().fetch(id);
        return this;
    }
    
    public UserModel destroy(ApplicationContext context) {
        context.getUserRepository().delete(entity);
        return this;
    }
}
