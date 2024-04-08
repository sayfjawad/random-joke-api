package nl.multicode.joke.client;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
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
public class ExternalClientIntegrationTest {

    public static final String JOKES = """
            {
                "error": false,
                "amount": 10,
                "jokes": [
                    {
                        "category": "Programming",
                        "type": "single",
                        "joke": "UDP is better in the COVID era since it avoids unnecessary handshakes.",
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
                    },
                    {
                        "category": "Misc",
                        "type": "single",
                        "joke": "In Soviet Russia, gay sex gets you arrested. In America, getting arrested gets you gay sex.",
                        "flags": {
                            "nsfw": true,
                            "religious": false,
                            "political": false,
                            "racist": false,
                            "sexist": false,
                            "explicit": true
                        },
                        "safe": false,
                        "id": 289,
                        "lang": "en"
                    },
                    {
                        "category": "Programming",
                        "type": "single",
                        "joke": "Have a great weekend!\\nI hope your code behaves the same on Monday as it did on Friday.",
                        "flags": {
                            "nsfw": false,
                            "religious": false,
                            "political": false,
                            "racist": false,
                            "sexist": false,
                            "explicit": false
                        },
                        "id": 44,
                        "safe": true,
                        "lang": "en"
                    },
                    {
                        "category": "Pun",
                        "type": "single",
                        "joke": "How do you make holy water? You boil the hell out of it.",
                        "flags": {
                            "nsfw": false,
                            "religious": true,
                            "political": false,
                            "racist": false,
                            "sexist": false,
                            "explicit": false
                        },
                        "id": 202,
                        "safe": false,
                        "lang": "en"
                    },
                    {
                        "category": "Programming",
                        "type": "single",
                        "joke": "Documentation is like sex:\\nWhen it's good, it's very good.\\nWhen it's bad, it's better than nothing...",
                        "flags": {
                            "nsfw": true,
                            "religious": false,
                            "political": false,
                            "racist": false,
                            "sexist": false,
                            "explicit": false
                        },
                        "safe": false,
                        "id": 305,
                        "lang": "en"
                    },
                    {
                        "category": "Programming",
                        "type": "single",
                        "joke": "Debugging: Removing the needles from the haystack.",
                        "flags": {
                            "nsfw": false,
                            "religious": false,
                            "political": false,
                            "racist": false,
                            "sexist": false,
                            "explicit": false
                        },
                        "id": 40,
                        "safe": true,
                        "lang": "en"
                    },
                    {
                        "category": "Programming",
                        "type": "single",
                        "joke": "Being a self-taught developer is almost the same as being a cut neck chicken because you have no sense of direction in the beginning.",
                        "flags": {
                            "nsfw": false,
                            "religious": false,
                            "political": false,
                            "racist": false,
                            "sexist": false,
                            "explicit": false
                        },
                        "id": 184,
                        "safe": false,
                        "lang": "en"
                    },
                    {
                        "category": "Dark",
                        "type": "single",
                        "joke": "My ex had an accident. I told the paramedics the wrong blood type for her. She'll finally experience what rejection is really like.",
                        "flags": {
                            "nsfw": false,
                            "religious": false,
                            "political": false,
                            "racist": false,
                            "sexist": false,
                            "explicit": false
                        },
                        "id": 152,
                        "safe": false,
                        "lang": "en"
                    },
                    {
                        "category": "Dark",
                        "type": "single",
                        "joke": "My grandfather says I'm too reliant on technology.\\nI called him a hypocrite and unplugged his life support.",
                        "flags": {
                            "nsfw": false,
                            "religious": false,
                            "political": false,
                            "racist": false,
                            "sexist": false,
                            "explicit": false
                        },
                        "id": 178,
                        "safe": false,
                        "lang": "en"
                    },
                    {
                        "category": "Misc",
                        "type": "single",
                        "joke": "My husband and I were happy for 20 years. And then we met.",
                        "flags": {
                            "nsfw": false,
                            "religious": false,
                            "political": false,
                            "racist": false,
                            "sexist": false,
                            "explicit": false
                        },
                        "safe": true,
                        "id": 273,
                        "lang": "en"
                    }
                ]
            }
            """;

    @Autowired
    private ExternalJokeClient client;

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
    @DisplayName("When calling the Feign client with WireMock, then return 10 jokes")
    void testFeignClientWithWireMock() {
        // When
        final var response = client.fetchJokes();

        // Then
        assertThat(response.getAmount()).isEqualTo(10);
    }
}
