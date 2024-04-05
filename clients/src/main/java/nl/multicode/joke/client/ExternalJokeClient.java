package nl.multicode.joke.client;

import dev.jokeapi.v2.openapi.model.Joke;
import dev.jokeapi.v2.openapi.model.JokesResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "example", url = "https://v2.jokeapi.dev/")
public interface ExternalJokeClient {

    @GetMapping("/joke/Any?type=single&amount=16")
    JokesResponse getJokes();
}