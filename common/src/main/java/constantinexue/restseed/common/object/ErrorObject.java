package constantinexue.restseed.common.object;


public class ErrorObject implements ValueObject {
    
    private Integer code;
    
    private String message;
    
    public ErrorObject() {
        
    }
    
    public ErrorObject(int code, String message) {
        setCode(code).setMessage(message);
    }
    
    public Integer getCode() {
        return code;
    }
    
    public ErrorObject setCode(Integer code) {
        this.code = code;
        return this;
    }
    
    public String getMessage() {
        return message;
    }
    
    public ErrorObject setMessage(String message) {
        this.message = message;
        return this;
    }
    
}
