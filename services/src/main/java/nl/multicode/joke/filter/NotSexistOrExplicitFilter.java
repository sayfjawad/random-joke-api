package nl.multicode.joke.filter;

import java.util.function.Predicate;
import nl.multicode.joke.model.RandomJokeDto;
import org.springframework.stereotype.Component;

@Component
public class NotSexistOrExplicitFilter implements Predicate<RandomJokeDto> {

    @Override
    public boolean test(final RandomJokeDto randomJokeDto) {

        final var isNotSexist = !randomJokeDto.getFlags().isSexist();
        final var isNotExplicit = !randomJokeDto.getFlags().isExplicit();
        return isNotSexist && isNotExplicit;
    }
}
