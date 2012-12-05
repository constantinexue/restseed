package constantinexue.restseed.model;

import org.eclipse.jetty.util.security.Credential.MD5;

import constantinexue.restseed.core.ApplicationContext;
import constantinexue.restseed.entity.UserEntity;
import constantinexue.restseed.util.IDUtils;

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
    
    public UserModel(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.entity = userEntity;
        this.isNew = false;
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return entity.getUsername();
    }
    
    public UserModel setName(String name) {
        entity.setUsername(name);
        return this;
    }
    
    public UserModel setPassword(String plainPassword) {
        String password = MD5.digest(plainPassword);
        entity.setPassword(password);
        return this;
    }
    
    public UserModel save(ApplicationContext context) {
        if (isNew) {
            // 设置创建时间
            entity.setCreatedAt(context.timeService().getNowDateTime().toDate());
            context.userRepository().create(entity);
            entity = context.userRepository().fetch(id);
            isNew = false;
        }
        else {
            
        }
        return this;
    }
    
    public UserModel load() {
        
        return this;
    }
}
