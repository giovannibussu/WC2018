package worldcup;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import worldcup.model.Error;


@ControllerAdvice
public class ControllerAdvisor extends AbstractControllerAdvisor {


	protected ResponseEntity<Object> toEntity(Exception ex, HttpStatus status) {
		Error error = new Error();

		error.setMessage(ex.getMessage());
		return new ResponseEntity<>(error, status);
	} 


}
