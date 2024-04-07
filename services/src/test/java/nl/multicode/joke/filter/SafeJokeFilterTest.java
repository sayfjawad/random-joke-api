package nl.multicode.joke.filter;

import static org.assertj.core.api.Assertions.assertThat;

import nl.multicode.joke.model.RandomJokeDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SafeJokeFilterTest {

    private final SafeJokeFilter filter = new SafeJokeFilter();

    @Test
    @DisplayName("Given a safe joke, "
            + "when testing the filter, "
            + "then the filter should return true.")
    public void testTest_whenSafeJoke_thenReturnTrue() {
        // Given
        RandomJokeDto randomJokeDto = new RandomJokeDto();
        randomJokeDto.setSafe(true);

        // When
        boolean result = filter.test(randomJokeDto);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Given a non-safe joke, "
            + "when testing the filter, "
            + "then the filter should return false.")
    public void testTest_whenNonSafeJoke_thenReturnFalse() {
        // Given
        RandomJokeDto randomJokeDto = new RandomJokeDto();
        randomJokeDto.setSafe(false);

        // When
        boolean result = filter.test(randomJokeDto);

        // Then
        assertThat(result).isFalse();
    }
}
