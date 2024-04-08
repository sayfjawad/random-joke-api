package nl.multicode.joke.controller;

import java.util.Optional;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import nl.multicode.joke.model.RandomJokeDto;
import nl.multicode.joke.openapi.model.RandomJoke;
import nl.multicode.joke.service.JokeService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/joke")
@RestController
@RequiredArgsConstructor
public class RandomJokeController implements JokeController<ResponseEntity<RandomJoke>> {

    private final JokeService<Optional<RandomJokeDto>> service;

    private final ModelMapper modelMapper;

    @GetMapping("/fetch")
    public ResponseEntity<RandomJoke> getRandomJoke() {

        return service.fetch()
                .map(mapJokeDtoToResponseEntity())
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private Function<RandomJokeDto, ResponseEntity<RandomJoke>> mapJokeDtoToResponseEntity() {

        return jokeDto -> ResponseEntity.ok(modelMapper.map(jokeDto, RandomJoke.class));
    }
}
