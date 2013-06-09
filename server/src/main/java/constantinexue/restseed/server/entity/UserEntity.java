package constantinexue.restseed.server.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class UserEntity extends PersistanceEntity {
    
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    
    @Id
    @Column(name = "id")
    private String id;
    
    @Column(name = USERNAME)
    private String username;
    
    @Column(name = PASSWORD)
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
        return username;
    }
    
    public UserEntity setUsername(String name) {
        this.username = name;
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
