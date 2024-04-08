package nl.multicode.joke.handle.error;

import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDetails {

    private OffsetDateTime timestamp;

    private String message;

    private String details;

    public ErrorDetails(OffsetDateTime timestamp, String message, String details) {

        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}