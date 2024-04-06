package nl.multicode.joke.compare;

import java.util.Comparator;
import nl.multicode.joke.model.RandomJokeDto;
import org.springframework.stereotype.Component;

@Component
public class ShortestJokeComparator implements Comparator<RandomJokeDto> {

    @Override
    public int compare(final RandomJokeDto joke1, final RandomJokeDto joke2) {

        return Integer.compare(joke1.getJoke().length(), joke2.getJoke().length());
    }
}
