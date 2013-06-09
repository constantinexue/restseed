package constantinexue.restseed.common.exception;

public class UnknownErrorException extends ServiceException {
    
    /**
     * 
     */
    private static final long serialVersionUID = 3267078228835207304L;
    
    public UnknownErrorException() {
        super(500000);
    }
}
