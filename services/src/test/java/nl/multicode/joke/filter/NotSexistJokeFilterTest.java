package nl.multicode.joke.filter;

import static org.assertj.core.api.Assertions.assertThat;

import nl.multicode.joke.model.FlagsDto;
import nl.multicode.joke.model.RandomJokeDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class NotSexistJokeFilterTest {

    private final NotSexistJokeFilter filter = new NotSexistJokeFilter();

    @Test
    @DisplayName("Given a joke that is not sexist, "
            + "when testing the filter, "
            + "then the filter should return true.")
    public void testTest_whenNotSexistOrExplicit_thenReturnTrue() {
        // Given
        final var randomJokeDto = new RandomJokeDto();
        randomJokeDto.setFlags(FlagsDto.builder().build());

        // When
        final var result = filter.test(randomJokeDto);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Given a joke that is sexist, "
            + "when testing the filter, "
            + "then the filter should return false.")
    public void testTest_whenSexist_thenReturnFalse() {
        // Given
        final var randomJokeDto = new RandomJokeDto();
        randomJokeDto.setFlags(FlagsDto.builder()
                .sexist(true)
                .build());

        // When
        final var result = filter.test(randomJokeDto);

        // Then
        assertThat(result).isFalse();
    }


    @Test
    @DisplayName("Given a joke that is both sexist and explicit, "
            + "when testing the filter, "
            + "then the filter should return false.")
    public void testTest_whenSexistAndExplicit_thenReturnFalse() {
        // Given
        final var randomJokeDto = new RandomJokeDto();
        randomJokeDto.setFlags(FlagsDto.builder()
                .sexist(true)
                .explicit(true)
                .build());

        // When
        final var result = filter.test(randomJokeDto);

        // Then
        assertThat(result).isFalse();
    }
}
