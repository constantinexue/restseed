package constantinexue.restseed.common.exception;

public class UserNotExistException extends ServiceException {
    
    /**
     * 
     */
    private static final long serialVersionUID = -6210797003428167717L;
    
    public UserNotExistException() {
        super(404100);
    }
}
