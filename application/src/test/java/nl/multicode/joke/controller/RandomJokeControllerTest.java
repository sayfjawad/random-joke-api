package nl.multicode.joke.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import nl.multicode.joke.model.RandomJokeDto;
import nl.multicode.joke.openapi.model.RandomJoke;
import nl.multicode.joke.service.JokeService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class RandomJokeControllerTest {

    @Mock
    private JokeService<Optional<RandomJokeDto>> jokeService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RandomJokeController randomJokeController;

    @Test
    @DisplayName("When a request to fetch a random joke is made and the joke service returns a joke, then the controller should return an OK response with the joke.")
    public void testGetRandomJoke_whenJokeServiceReturnsJoke_thenReturnOkResponse() {
        // Given
        RandomJokeDto jokeDto = new RandomJokeDto();
        when(jokeService.fetch()).thenReturn(Optional.of(jokeDto));
        RandomJoke randomJoke = new RandomJoke();
        when(modelMapper.map(jokeDto, RandomJoke.class)).thenReturn(randomJoke);

        // When
        ResponseEntity<RandomJoke> response = randomJokeController.getRandomJoke();

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(randomJoke);
    }

    @Test
    @DisplayName("When a request to fetch a random joke is made and the joke service returns empty, then the controller should return a Not Found response.")
    public void testGetRandomJoke_whenJokeServiceReturnsEmpty_thenReturnNotFoundResponse() {
        // Given
        when(jokeService.fetch()).thenReturn(Optional.empty());

        // When
        ResponseEntity<RandomJoke> response = randomJokeController.getRandomJoke();

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @DisplayName("When a request to fetch a random joke is made and the joke service returns empty, then the service should be called.")
    public void testGetRandomJoke_whenJokeServiceReturnsEmpty_thenServiceShouldBeCalled() {
        // Given
        when(jokeService.fetch()).thenReturn(Optional.empty());

        // When
        randomJokeController.getRandomJoke();

        // Then
        verify(jokeService, times(1)).fetch();
    }
}
