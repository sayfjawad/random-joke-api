package nl.multicode.joke.handle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import feign.RetryableException;
import nl.multicode.joke.handle.error.ErrorDetails;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

@RunWith(MockitoJUnitRunner.class)
public class GlobalExceptionHandlerTest {

    @Mock
    private WebRequest webRequest;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    @DisplayName("Given a RetryableException is thrown, "
            + "when handling the exception, "
            + "then the controller should return a Not Found response.")
    public void testResourceNotFoundException() {
        // Given
        final var ex = mock(RetryableException.class);
        when(ex.getMessage()).thenReturn("Service unavailable");

        // When
        final var responseEntity = globalExceptionHandler.resourceNotFoundException(ex, webRequest);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isInstanceOf(ErrorDetails.class);
        ErrorDetails errorDetails = (ErrorDetails) responseEntity.getBody();
        assertThat(errorDetails.getMessage()).contains("Unable to retrieve jokes from server!");
        assertThat(errorDetails.getTimestamp()).isNotNull();
    }
}
