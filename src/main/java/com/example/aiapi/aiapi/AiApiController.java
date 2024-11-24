package com.example.aiapi.aiapi;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/openai")
public class AiApiController {
    private final WebClient webClient;

    public AiApiController(WebClient webClient) {
        this.webClient = webClient;
    }

    @PostMapping("/translate")
    public Mono<String> translate(@RequestBody TranslationRequest request) {
        return webClient.post()
                .uri("/chat/completions")
                .bodyValue(request.toApiRequest())
                .retrieve()
                .bodyToMono(String.class);
    }

    @PostMapping("/translate/stream")
    public Flux<String> translateStream(@RequestBody TranslationRequest request) {
        return webClient.post()
                .uri("/chat/completions")
                .bodyValue(request.toApiRequest())
                .retrieve()
                .bodyToFlux(String.class);
    }
}
