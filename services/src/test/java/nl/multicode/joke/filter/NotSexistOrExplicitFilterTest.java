package nl.multicode.joke.filter;

import static org.assertj.core.api.Assertions.assertThat;

import nl.multicode.joke.model.FlagsDto;
import nl.multicode.joke.model.RandomJokeDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NotSexistOrExplicitFilterTest {

    private final NotExplicitJokeFilter filter = new NotExplicitJokeFilter();

    @Test
    @DisplayName("Given a joke that is not explicit, "
            + "when testing the filter, "
            + "then the filter should return true.")
    void testTest_whenNotSexistOrExplicit_thenReturnTrue() {
        // Given
        final var randomJokeDto = RandomJokeDto.builder().build();
        randomJokeDto.setFlags(FlagsDto.builder().build());

        // When
        final var result = filter.test(randomJokeDto);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Given a joke that is explicit, "
            + "when testing the filter, "
            + "then the filter should return false.")
    void testTest_whenExplicit_thenReturnFalse() {
        // Given
        final var randomJokeDto = RandomJokeDto.builder()
                .flags(FlagsDto.builder().explicit(true).build())
                .build();

        // When
        final var result = filter.test(randomJokeDto);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Given a joke that is both sexist and explicit, "
            + "when testing the filter, "
            + "then the filter should return false.")
    void testTest_whenSexistAndExplicit_thenReturnFalse() {
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
