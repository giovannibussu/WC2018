package worldcup.main;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

public abstract class AbstractControllerAdvisor extends ResponseEntityExceptionHandler {

	protected ResponseEntity<Object> handleExceptionInternal(
			Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {


		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
			request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
		}

		return toEntity(ex, status);

	}

	@ExceptionHandler({ConflittoException.class})
	public ResponseEntity<Object> handleConflict(RuntimeException ex) {
		return toEntity(ex, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(ConversionFailedException.class)
	public ResponseEntity<Object> handleConversionFailedException(RuntimeException ex) {
		return toEntity(ex, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<Object> handleBadRequest(RuntimeException ex) {
		return toEntity(ex, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({NotFoundException.class})
	public ResponseEntity<Object> handleContactNotFound(RuntimeException ex) {
		return toEntity(ex, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({NotAuthorizedException.class})
	public ResponseEntity<Object> handleNotAuthorized(RuntimeException ex) {
		return toEntity(ex, HttpStatus.FORBIDDEN);
	}


	protected abstract ResponseEntity<Object> toEntity(Exception ex, HttpStatus status);

	@ExceptionHandler({InternalException.class})
	public final ResponseEntity<Object> handleAllInternalExceptions(InternalException ex, WebRequest request) {
		return toEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
