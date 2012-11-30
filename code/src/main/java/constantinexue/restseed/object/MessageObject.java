package constantinexue.restseed.object;

import org.joda.time.LocalDateTime;

public class MessageObject implements ValueObject {
    
    public String id;
    public String text;
    public LocalDateTime createdAt;
}
