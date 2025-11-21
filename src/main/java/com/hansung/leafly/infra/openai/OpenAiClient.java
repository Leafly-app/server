package com.hansung.leafly.infra.openai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hansung.leafly.infra.openai.dto.RecommendAiRes;
import com.hansung.leafly.infra.openai.exception.OpenaiRequestFailed;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class OpenAiClient {

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    public RecommendAiRes recommendBooks(String prompt) {
        try {
            Map<String, Object> requestBody = Map.of(
                    "model", "gpt-4o-mini",
                    "response_format", Map.of("type", "json_object"),
                    "messages", List.of(
                            Map.of("role", "user", "content", prompt)
                    )
            );

            var headers = new org.springframework.http.HttpHeaders();
            headers.setBearerAuth(apiKey);
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

            var entity = new org.springframework.http.HttpEntity<>(requestBody, headers);

            String response = restTemplate.postForObject(
                    "https://api.openai.com/v1/chat/completions",
                    entity,
                    String.class
            );

            JsonNode root = mapper.readTree(response);
            String content = root.path("choices").get(0).path("message").path("content").asText();

            return mapper.readValue(content, RecommendAiRes.class);

        } catch (Exception e) {
            throw new OpenaiRequestFailed();
        }
    }
}

