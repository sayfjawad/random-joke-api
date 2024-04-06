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

    private Integer id;
    private String category;
    private String type;
    private boolean safe;
    private String joke;
    private FlagsDto flags;
    private String lang;
}
