package nl.multicode.joke.service;

import lombok.RequiredArgsConstructor;
import nl.multicode.joke.client.ExternalJokeClient;
import nl.multicode.joke.model.RandomJokeDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RandomJokeService implements JokeService<RandomJokeDto> {

    private final ExternalJokeClient jokeClient;


    @Override
    public RandomJokeDto fetch() {

        return jokeClient.getJokes().getJokes().stream()
                .map(joke -> RandomJokeDto.builder()
                        .id(joke.getId())
                        .joke(joke.getJoke())
                        .build())
                .findFirst()
                .orElse(RandomJokeDto.builder().build());
    }
}
