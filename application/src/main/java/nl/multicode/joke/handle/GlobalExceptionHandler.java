package nl.multicode.joke.handle;

import feign.RetryableException;
import java.time.OffsetDateTime;
import nl.multicode.joke.handle.error.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RetryableException.class)
    public ResponseEntity<?> resourceNotFoundException(RetryableException ex, WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(OffsetDateTime.now(),
                "Unable to retrieve jokes from server! \n" + ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
}