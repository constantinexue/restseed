package constantinexue.restseed.common.object;

public class RootObject implements ValueObject {
    
    private String version;
    
    private ErrorObject error;
    
    private Object data;
    
    public RootObject() {
        version = "3.0";
    }
    
    public String getVersion() {
        return version;
    }
    
    public ErrorObject getError() {
        return error;
    }
    
    public RootObject setError(ErrorObject error) {
        this.error = error;
        return this;
    }
    
    public Object getData() {
        return data;
    }
    
    public RootObject setData(Object data) {
        this.data = data;
        return this;
    }
    
}
