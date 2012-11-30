package constantinexue.restseed.model;

import constantinexue.restseed.entity.UserEntity;
import constantinexue.restseed.util.IDUtils;

public class UserModel {
    
    private String id;
    private UserEntity entity;
    
    public UserModel() {
        this.id = IDUtils.generate();
        this.entity = new UserEntity();
    }
    
    public UserModel(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.entity = userEntity;
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
