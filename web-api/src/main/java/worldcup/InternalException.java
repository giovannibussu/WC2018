package worldcup;

public class InternalException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InternalException(String message) {
		super(message);
	}
	
	public InternalException(Throwable t) {
		super(t);
	}
}
