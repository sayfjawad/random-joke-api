package nl.multicode.joke.model;

import lombok.Builder;

@Builder
public record RandomJokeDto(Integer id, String joke) {

}
