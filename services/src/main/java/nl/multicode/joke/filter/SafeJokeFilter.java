package nl.multicode.joke.filter;

import java.util.function.Predicate;
import nl.multicode.joke.model.RandomJokeDto;
import org.springframework.stereotype.Component;

@Component
public class SafeJokeFilter implements Predicate<RandomJokeDto> {

    @Override
    public boolean test(final RandomJokeDto randomJokeDto) {

        return randomJokeDto.isSafe();
    }
}
