package nl.multicode.joke.controller;

import lombok.RequiredArgsConstructor;
import nl.multicode.joke.model.RandomJokeDto;
import nl.multicode.joke.openapi.model.RandomJoke;
import nl.multicode.joke.service.JokeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/joke")
@RestController
@RequiredArgsConstructor
public class RandomJokeController implements
        JokeController<ResponseEntity<RandomJoke>> {

    private final JokeService<RandomJokeDto> service;

    @GetMapping("/fetch")
    public ResponseEntity<RandomJoke> fetch() {

        final RandomJokeDto randomJokeDto = service.fetch();
        return ResponseEntity.ok(RandomJoke.builder()
                .id(randomJokeDto.id())
                .joke(randomJokeDto.joke())
                .build());
    }
}
