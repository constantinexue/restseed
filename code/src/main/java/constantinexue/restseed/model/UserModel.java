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
        return entity.getName();
    }
    
    public UserModel setName(String name) {
        entity.setName(name);
        return this;
    }
    
    public UserModel setPassword(String plainPassword) {
        String password = MD5.digest(plainPassword);
        entity.setPassword(password);
        return this;
    }
    
    public UserModel save(ApplicationContext context) {
        if (isNew) {
            entity = context.getUserRepository().persist(entity);
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
