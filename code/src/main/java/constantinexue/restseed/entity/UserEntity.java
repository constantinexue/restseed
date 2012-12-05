package constantinexue.restseed.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class UserEntity extends PersistanceEntity {
    
    @Id
    @Column(name = "id")
    private String id;
    
    @Column(name = "username")
    private String name;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "created")
    private Date createdAt;
    
    @Override
    public String getId() {
        return id;
    }
    
    public UserEntity setId(String id) {
        this.id = id;
        return this;
    }
    
    public String getUsername() {
        return name;
    }
    
    public UserEntity setUsername(String name) {
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
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public UserEntity setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }
    
}
