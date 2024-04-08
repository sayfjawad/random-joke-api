package nl.multicode.joke.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import java.util.Objects;
import nl.multicode.joke.client.config.WireMockConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ActiveProfiles("test")
@EnableFeignClients
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WireMockConfig.class})
class RandomJokeControllerIntegrationTest {

    public static final String JOKES = """
            {
                "error": false,
                "amount": 10,
                "jokes": [
                    {
                        "category": "Programming",
                        "type": "single",
                        "joke": "Long safe joke that should be ignored",
                        "flags": {
                            "nsfw": false,
                            "religious": false,
                            "political": false,
                            "racist": false,
                            "sexist": false,
                            "explicit": false
                        },
                        "id": 259,
                        "safe": false,
                        "lang": "en"
                    },{
                        "category": "Programming",
                        "type": "single",
                        "joke": "Short and safe",
                        "flags": {
                            "nsfw": false,
                            "religious": false,
                            "political": false,
                            "racist": false,
                            "sexist": false,
                            "explicit": false
                        },
                        "id": 259,
                        "safe": true,
                        "lang": "en"
                    },{
                        "category": "Programming",
                        "type": "single",
                        "joke": "Unsafe joke that will be ignored",
                        "flags": {
                            "nsfw": false,
                            "religious": false,
                            "political": false,
                            "racist": false,
                            "sexist": true,
                            "explicit": false
                        },
                        "id": 259,
                        "safe": false,
                        "lang": "en"
                    }
                ]
            }
            """;

    @Autowired
    private RandomJokeController controller;

    @Autowired
    private WireMockServer mockServer;

    private static void setupMockJokesResponse(WireMockServer mockService) {
        // Given
        mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/joke/Any?type=single&amount=16"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(JOKES)));
    }

    @BeforeEach
    void setUp() {
        // Given
        setupMockJokesResponse(mockServer);
    }

    @Test
    @DisplayName("When calling the Feign client with WireMock, then return a short and safe joke")
    void testFeignClientWithWireMock() {
        // When
        final var response = controller.getRandomJoke();
        final var joke = Objects.requireNonNull(response.getBody()).getJoke();

        // Then
        assertThat(joke).isEqualTo("Short and safe");
    }
}
