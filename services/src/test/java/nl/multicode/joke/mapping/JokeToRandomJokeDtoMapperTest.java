package nl.multicode.joke.mapping;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import dev.jokeapi.v2.openapi.model.Joke;
import nl.multicode.joke.model.RandomJokeDto;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

@RunWith(MockitoJUnitRunner.class)
public class JokeToRandomJokeDtoMapperTest {

    @Mock
    private ModelMapper modelMapperMock;
    @InjectMocks
    private JokeToRandomJokeDtoMapper mapper;

    @Test
    @DisplayName("Given a valid Joke object, "
            + "when applying the mapper, "
            + "then the mapper should map it to a RandomJokeDto object.")
    public void testApply_validJokeObject_shouldMapToRandomJokeDto() {
        // Given
        final var joke = new Joke();
        joke.setCategory("Programming");
        joke.setType("single");
        joke.setJoke("Why did the programmer quit his job? Because he didn't get arrays.");

        // Mocking the behavior of ModelMapper
        final var expectedDto = new RandomJokeDto();
        expectedDto.setJoke("Why did the programmer quit his job? Because he didn't get arrays.");
        when(modelMapperMock.map(joke, RandomJokeDto.class)).thenReturn(expectedDto);

        // When
        final var result = mapper.apply(joke);

        // Then
        assertThat(result).isNotNull()
                .isEqualTo(expectedDto);
    }
}
