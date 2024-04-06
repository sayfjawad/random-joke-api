package nl.multicode.joke.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlagsDto {

    //Only applicable flags are mapped
    private boolean sexist;
    private boolean explicit;
}
