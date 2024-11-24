package com.example.aiapi.aiapi;


import org.json.JSONObject;
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
        // 요청 유효성 검사
        if (request.getPrompt() == null || request.getPrompt().isEmpty()) {
            return Mono.error(new IllegalArgumentException("Prompt must not be null or empty."));
        }
        if (request.getMaxTokens() <= 0) {
            return Mono.error(new IllegalArgumentException("MaxTokens must be greater than 0."));
        }

        JSONObject apiRequest = request.toApiRequest();

        // 요청 본문 로그 출력
        System.out.println("Request Body: " + apiRequest.toString());

        return webClient.post()
                .uri("/chat/completions")
                .bodyValue(apiRequest.toString())
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> System.out.println("Response: " + response))
                .doOnError(error -> System.err.println("Error: " + error.getMessage()));
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
