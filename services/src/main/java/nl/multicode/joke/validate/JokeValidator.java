package nl.multicode.joke.validate;

import nl.multicode.joke.model.RandomJokeDto;

public class JokeValidator implements ValidJoke {

    @Override
    public boolean test(RandomJokeDto joke) {

        return true;
    }
}
