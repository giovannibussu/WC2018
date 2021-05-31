package worldcup;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;


@ControllerAdvice
public class ControllerAdvisor extends AbstractControllerAdvisor {


	protected ResponseEntity<Object> toEntity(Exception ex, HttpStatus status) {
		Error error = new Error();

		return new ResponseEntity<>(error, status);
	} 


}
