package constantinexue.guicelike.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "message")
public class MessageEntity {
    
    @Id
    @Column(name = "id")
    private String id;
    
    @Column(name = "text")
    private String text;
    
    @Column(name = "created")
    private Date createdAt;
    
    public String getId() {
        return id;
    }
    
    public MessageEntity setId(String id) {
        this.id = id;
        return this;
    }
    
    public String getText() {
        return text;
    }
    
    public MessageEntity setText(String text) {
        this.text = text;
        return this;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public MessageEntity setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }
    
}
