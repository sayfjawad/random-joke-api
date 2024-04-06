package nl.multicode.joke.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RandomJokeDto {

    //Only applicable flags are mapped
    private Integer id;
    private boolean safe;
    private String joke;
    private FlagsDto flags;
}
