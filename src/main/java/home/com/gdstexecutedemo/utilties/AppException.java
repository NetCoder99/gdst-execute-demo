package home.com.gdstexecutedemo.utilties;

public class AppException extends Exception {
	private static final long serialVersionUID = 1L;

	public AppException(Exception ex) {
        super(ex.getLocalizedMessage(),null,false,false);
    }
	
	public AppException(String message) {
        super(message,null,false,false);
    }
}
