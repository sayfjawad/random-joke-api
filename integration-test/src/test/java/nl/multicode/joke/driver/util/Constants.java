package nl.multicode.joke.driver.util;

import java.net.URI;
import java.net.URISyntaxException;

public class Constants {

    public static final URI ENDPOINT_URI;

    static {
        try {
            ENDPOINT_URI = new URI(
                    String.format("http://%s:8080", Env.INSTANCE.get("host", "localhost")));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private Constants() {

    }
}
