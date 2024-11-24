package com.example.aiapi.aiapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.json.JSONObject;
import java.util.*;

@Data
@AllArgsConstructor
public class TranslationRequest {
    private String prompt;
    private int maxTokens;

    public JSONObject toApiRequest() {
        JSONObject request = new JSONObject();
        request.put("model", "gpt-4"); // OpenAI의 GPT-4 모델 사용
        request.put("messages", List.of(
                Map.of("role", "system", "content", "Simplify the following text."),
                Map.of("role", "user", "content", prompt)
        ));
        request.put("max_tokens", maxTokens);
        return request;
    }
}

