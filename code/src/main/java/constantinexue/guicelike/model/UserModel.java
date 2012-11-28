package constantinexue.guicelike.model;

import constantinexue.guicelike.entity.UserEntity;
import constantinexue.guicelike.util.IDUtils;

public class UserModel {
    
    private String id;
    private UserEntity entity;
    
    public UserModel() {
        id = IDUtils.generate();
        entity = new UserEntity();
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
    
    public UserModel load() {
        
        return this;
    }
}
