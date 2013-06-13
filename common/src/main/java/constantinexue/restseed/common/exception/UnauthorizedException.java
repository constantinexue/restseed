package constantinexue.restseed.common.exception;


public class UnauthorizedException extends ServiceException {

    /**
     * 
     */
    private static final long serialVersionUID = 7367642902011090721L;

    protected UnauthorizedException() {
        super(401100);
    }
    
}
