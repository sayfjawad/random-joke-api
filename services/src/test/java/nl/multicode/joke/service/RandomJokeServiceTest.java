package nl.multicode.joke.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.jokeapi.v2.openapi.model.Flags;
import dev.jokeapi.v2.openapi.model.Joke;
import dev.jokeapi.v2.openapi.model.JokesResponse;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import nl.multicode.joke.client.ExternalJokeClient;
import nl.multicode.joke.compare.ShortestJokeComparator;
import nl.multicode.joke.filter.SafeJokeFilter;
import nl.multicode.joke.mapping.JokeToRandomJokeDtoMapper;
import nl.multicode.joke.model.FlagsDto;
import nl.multicode.joke.model.RandomJokeDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RandomJokeServiceTest {

    @Mock
    private ExternalJokeClient jokeClient;

    @Mock
    private JokeToRandomJokeDtoMapper randomJokeDtoMapper;

    @Mock
    private ShortestJokeComparator shortestJokeComparator;

    @Mock
    private SafeJokeFilter safeJokeFilter;

    private List<Predicate<RandomJokeDto>> passJokeThroughFilters;

    @InjectMocks
    private RandomJokeService randomJokeService;

    @Before
    public void setup() {

        passJokeThroughFilters = List.of(safeJokeFilter);
    }

    @Test
    @DisplayName("Given jokes are fetched from external client, "
            + "when filtering jokes for safety and length, "
            + "then return the shortest safe joke")
    public void testFetch_whenJokesAreFiltered_returnShortestNonFilteredJoke() {
        // Given
        final var longJoke = Joke.builder()
                .joke("This is a long joke")
                .build();
        final var shortJoke = Joke.builder()
                .joke("Short joke")
                .build();
        final var sexistJoke = Joke.builder()
                .joke("Sexist joke")
                .safe(false)
                .flags(Flags.builder()
                        .sexist(true)
                        .build())
                .build();
        final var jokesResponse = JokesResponse.builder()
                .jokes(Arrays.asList(sexistJoke, longJoke, shortJoke))
                .build();
        when(jokeClient.fetchJokes()).thenReturn(jokesResponse);

        final var sexistJokeDto = RandomJokeDto.builder()
                .joke("Sexist joke")
                .safe(false)
                .flags(FlagsDto.builder()
                        .sexist(true)
                        .build())
                .build();
        final var longJokeDto = RandomJokeDto.builder()
                .joke("This is a long joke")
                .build();
        final var shortJokeDto = RandomJokeDto.builder()
                .joke("Short joke")
                .build();
        when(randomJokeDtoMapper.apply(sexistJoke)).thenReturn(sexistJokeDto);
        when(randomJokeDtoMapper.apply(longJoke)).thenReturn(longJokeDto);
        when(randomJokeDtoMapper.apply(shortJoke)).thenReturn(shortJokeDto);
        when(shortestJokeComparator.compare(longJokeDto, shortJokeDto)).thenReturn(
                1); // joke2 is shorter
        when(safeJokeFilter.test(longJokeDto)).thenReturn(true);
        when(safeJokeFilter.test(shortJokeDto)).thenReturn(true);
        randomJokeService = new RandomJokeService(jokeClient,
                randomJokeDtoMapper,
                passJokeThroughFilters,
                shortestJokeComparator);

        // When
        final var result = randomJokeService.fetch();

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getJoke()).isEqualTo("Short joke");

        // Verify that jokes are mapped and filtered
        verify(randomJokeDtoMapper, times(3)).apply(any(Joke.class));
        verify(shortestJokeComparator).compare(longJokeDto, shortJokeDto);
    }
}
