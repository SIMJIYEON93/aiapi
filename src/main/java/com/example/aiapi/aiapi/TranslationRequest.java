package com.example.aiapi.aiapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.json.JSONObject;

@Data
@AllArgsConstructor
public class TranslationRequest {
    private String prompt;
    private int maxTokens;

    public JSONObject toApiRequest() {
        JSONObject request = new JSONObject();
        request.put("model", "claude-3.5");
        request.put("prompt", prompt);
        request.put("max_tokens", maxTokens);
        return request;
    }
}

