package constantinexue.guicelike.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserEntity {
    
    @Id
    @Column(name = "id")
    private String id;
    
    @Column(name = "name")
    private String name;
    
    public String getId() {
        return id;
    }
    
    public UserEntity setId(String id) {
        this.id = id;
        return this;
    }
    
    public String getName() {
        return name;
    }
    
    public UserEntity setName(String name) {
        this.name = name;
        return this;
    }
    
}
