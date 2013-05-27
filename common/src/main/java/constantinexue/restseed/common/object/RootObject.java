package constantinexue.restseed.common.object;

public class RootObject<T> implements ValueObject {
    
    private String version;
    
    private ErrorObject error;
    
    private T data;
    
    public RootObject() {
        version = "3.0";
    }
    
    public String getVersion() {
        return version;
    }
    
    public ErrorObject getError() {
        return error;
    }
    
    public RootObject<T> setError(ErrorObject error) {
        this.error = error;
        return this;
    }
    
    public T getData() {
        return data;
    }
    
    public RootObject<T> setData(T data) {
        this.data = data;
        return this;
    }
    
}
