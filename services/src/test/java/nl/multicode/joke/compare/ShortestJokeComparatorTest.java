package nl.multicode.joke.compare;

import static org.assertj.core.api.Assertions.assertThat;

import nl.multicode.joke.model.RandomJokeDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ShortestJokeComparatorTest {

    private final ShortestJokeComparator comparator = new ShortestJokeComparator();

    @Test
    @DisplayName("Given two jokes with different lengths, "
            + "when comparing them, "
            + "then the comparator should return a negative value if the first joke is shorter.")
    void testCompare_whenFirstJokeIsShorter_thenNegativeValue() {
        // Given
        final var joke1 = new RandomJokeDto();
        joke1.setJoke("Short joke");
        final var joke2 = new RandomJokeDto();
        joke2.setJoke("Longer joke");

        // When
        int result = comparator.compare(joke1, joke2);

        // Then
        assertThat(result).isNegative();
    }

    @Test
    @DisplayName("Given two jokes with different lengths, "
            + "when comparing them, "
            + "then the comparator should return a positive value if the first joke is longer.")
    void testCompare_whenFirstJokeIsLonger_thenPositiveValue() {
        // Given
        final var longerJoke = RandomJokeDto.builder()
                .joke("Longer joke")
                .build();
        final var shortJoke = RandomJokeDto.builder()
                .joke("Short joke")
                .build();

        // When
        final var result = comparator.compare(longerJoke, shortJoke);

        // Then
        assertThat(result).isPositive();
    }

    @Test
    @DisplayName("Given two jokes with the same length, "
            + "when comparing them, "
            + "then the comparator should return zero.")
    void testCompare_whenJokesHaveSameLength_thenZero() {
        // Given
        final var joke1 = RandomJokeDto.builder()
                .joke("Equal length joke")
                .build();
        final var joke2 = RandomJokeDto.builder()
                .joke("Equal length joke")
                .build();

        // When
        final var result = comparator.compare(joke1, joke2);

        // Then
        assertThat(result).isZero();
    }
}
