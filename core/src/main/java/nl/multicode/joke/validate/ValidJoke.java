package nl.multicode.joke.validate;

import java.util.function.Predicate;
import nl.multicode.joke.model.RandomJokeDto;

public interface ValidJoke extends Predicate<RandomJokeDto> {

    default boolean test(RandomJokeDto joke) {

        return true;
    }
}
