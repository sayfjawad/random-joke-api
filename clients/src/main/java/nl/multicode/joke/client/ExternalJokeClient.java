package nl.multicode.joke.client;

import dev.jokeapi.v2.openapi.model.JokesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "jokesClient", url = "${jokeapi.url}")
public interface ExternalJokeClient {

    @GetMapping("/joke/Any?type=single&amount=16")
    JokesResponse fetchJokes();
}