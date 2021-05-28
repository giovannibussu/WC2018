package worldcup.main;

public class ConflittoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ConflittoException(String message) {
		super(message);
	}
	
	public ConflittoException(Throwable t) {
		super(t);
	}
}
