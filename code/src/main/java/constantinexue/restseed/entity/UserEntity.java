package constantinexue.restseed.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class UserEntity {
    
    @Id
    @Column(name = "id")
    private String id;
    
    @Column(name = "username")
    private String name;
    
    @Column(name = "password")
    private String password;
    
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
    
    public String getPassword() {
        return password;
    }
    
    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }
    
}
