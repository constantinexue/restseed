package constantinexue.restseed.common.object;

public class UserObject implements ValueObject {
    
    private String id;
    private String name;
    
    public String getId() {
        return id;
    }
    
    public UserObject setId(String id) {
        this.id = id;
        return this;
    }
    
    public String getName() {
        return name;
    }
    
    public UserObject setName(String name) {
        this.name = name;
        return this;
    }
    
}
