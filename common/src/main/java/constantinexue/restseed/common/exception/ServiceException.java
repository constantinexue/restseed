package constantinexue.restseed.common.exception;

public abstract class ServiceException extends RuntimeException {
    
    /**
     * 
     */
    private static final long serialVersionUID = 906697761637104229L;
    
    private int errorCode;
    
    protected ServiceException(int errorCode) {
        this.errorCode = errorCode;
    }
    
    public int getErrorCode() {
        return errorCode;
    }
}
