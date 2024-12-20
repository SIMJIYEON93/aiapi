package com.example.aiapi.aiapi;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/openai")
public class AiApiController {
    private final AiApiService aiApiService;

    public AiApiController(AiApiService aiApiService) {
        this.aiApiService = aiApiService;
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

        // 서비스 계층에 요청 위임
        return aiApiService.translate(request.getPrompt(), request.getMaxTokens());
    }

    @PostMapping("/translate/stream")
    public Flux<String> translateStream(@RequestBody TranslationRequest request) {
        // 요청 유효성 검사
        if (request.getPrompt() == null || request.getPrompt().isEmpty()) {
            return Flux.error(new IllegalArgumentException("Prompt must not be null or empty."));
        }
        if (request.getMaxTokens() <= 0) {
            return Flux.error(new IllegalArgumentException("MaxTokens must be greater than 0."));
        }

        // 서비스 계층에 요청 위임
        return aiApiService.translateStream(request.getPrompt(), request.getMaxTokens());
    }
}

