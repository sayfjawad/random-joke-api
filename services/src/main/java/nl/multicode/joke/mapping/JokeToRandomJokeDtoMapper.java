package nl.multicode.joke.mapping;

import dev.jokeapi.v2.openapi.model.Joke;
import java.util.function.Function;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import nl.multicode.joke.model.RandomJokeDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JokeToRandomJokeDtoMapper implements Function<@Valid Joke, RandomJokeDto> {

    private final ModelMapper mapper;

    @Override
    public RandomJokeDto apply(@Valid final Joke joke) {

        return mapper.map(joke, RandomJokeDto.class);
    }
}
