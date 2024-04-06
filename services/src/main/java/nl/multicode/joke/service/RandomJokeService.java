package nl.multicode.joke.service;

import dev.jokeapi.v2.openapi.model.JokesResponse;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.multicode.joke.client.ExternalJokeClient;
import nl.multicode.joke.compare.ShortestJokeComparator;
import nl.multicode.joke.mapping.JokeToRandomJokeDtoMapper;
import nl.multicode.joke.model.RandomJokeDto;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RandomJokeService implements JokeService<Optional<RandomJokeDto>> {

    private final ExternalJokeClient jokeClient;
    private final JokeToRandomJokeDtoMapper randomJokeDtoMapper;
    private final List<Predicate<RandomJokeDto>> passJokeThroughFilters;
    private final ShortestJokeComparator shortestJokeComparator;


    @Override
    public Optional<RandomJokeDto> fetch() {

        final var jokesResponse = jokeClient.fetchJokes();
        log.info("Received jokes: {}", jokesResponse.toString());

        return jokesResponse.getJokes().stream()
                .map(randomJokeDtoMapper)
                .filter(joke -> passJokeThroughFilters.stream().allMatch(filter -> filter.test(joke)))
                .min(shortestJokeComparator);
    }
}
