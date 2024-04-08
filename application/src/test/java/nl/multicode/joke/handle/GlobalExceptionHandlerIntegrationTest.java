package nl.multicode.joke.handle;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import feign.RetryableException;
import java.util.Optional;
import nl.multicode.joke.model.RandomJokeDto;
import nl.multicode.joke.service.JokeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class GlobalExceptionHandlerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JokeService<Optional<RandomJokeDto>> jokeService;

    @Test
    @DisplayName("When fetching a random joke, then return the joke")
    void whenGetRandomJoke_thenReturnJoke() throws Exception {
        // Given
        // When fetching a random joke
        when(jokeService.fetch()).thenReturn(Optional.of(RandomJokeDto.builder().build()));

        // Then
        mockMvc.perform(get("/api/joke/fetch"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("When service throws RetryableException, then handle as Not Found")
    void whenServiceThrowsRetryableException_thenHandleAsNotFound() throws Exception {
        // Given
        given(jokeService.fetch()).willThrow(mock(RetryableException.class));

        // When service throws RetryableException

        // Then
        mockMvc.perform(get("/api/joke/fetch"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
