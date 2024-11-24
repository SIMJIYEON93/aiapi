package com.example.aiapi.aiapi;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AiApiService {

    private final WebClient webClient;

    public AiApiService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> translate(String prompt, int maxTokens) {
        TranslationRequest request = new TranslationRequest(prompt, maxTokens);
        return webClient.post()
                .uri("/chat/completions")
                .bodyValue(request.toApiRequest())
                .retrieve()
                .bodyToMono(String.class);
    }

    public Flux<String> translateStream(String prompt, int maxTokens) {
        TranslationRequest request = new TranslationRequest(prompt, maxTokens);
        return webClient.post()
                .uri("/chat/completions")
                .bodyValue(request.toApiRequest())
                .retrieve()
                .bodyToFlux(String.class);
    }
}
