package constantinexue.restseed.server.object;

import org.joda.time.LocalDateTime;

public class MessageObject implements ValueObject {
    
    private String id;
    private String text;
    private LocalDateTime createdAt;
    private UserObject author;
    
    public String getId() {
        return id;
    }
    
    public MessageObject setId(String id) {
        this.id = id;
        return this;
    }
    
    public String getText() {
        return text;
    }
    
    public MessageObject setText(String text) {
        this.text = text;
        return this;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public MessageObject setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
    
    public UserObject getAuthor() {
        return author;
    }
    
    public MessageObject setAuthor(UserObject author) {
        this.author = author;
        return this;
    }
    
}
