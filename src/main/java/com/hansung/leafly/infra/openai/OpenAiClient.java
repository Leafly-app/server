package com.hansung.leafly.infra.openai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hansung.leafly.infra.openai.dto.BookSummaryAiRes;
import com.hansung.leafly.infra.openai.dto.RecommendAiRes;
import com.hansung.leafly.infra.openai.exception.OpenaiRequestFailed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class OpenAiClient {

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    // AI 추천 책 리스트
    public RecommendAiRes recommendBooks(String prompt) {
        return callOpenAi(prompt, RecommendAiRes.class);
    }

    public BookSummaryAiRes summarizeBook(String prompt) {
        return callOpenAi(prompt, BookSummaryAiRes.class);
    }

    // 공통 AI 호출 메소드
    private <T> T callOpenAi(String prompt, Class<T> responseType) {
        try {
            Map<String, Object> requestBody = Map.of(
                    "model", "gpt-4o-mini",
                    "response_format", Map.of("type", "json_object"),
                    "messages", List.of(
                            Map.of("role", "user", "content", prompt)
                    )
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<?> entity = new HttpEntity<>(requestBody, headers);

            String response = restTemplate.postForObject(
                    "https://api.openai.com/v1/chat/completions",
                    entity,
                    String.class
            );

            JsonNode root = mapper.readTree(response);
            String content = root.path("choices").get(0).path("message").path("content").asText();

            return mapper.readValue(content, responseType);

        } catch (Exception e) {
            log.error("[OpenAI ERROR] Exception 메시지: {}", e.getMessage());
            throw new OpenaiRequestFailed();
        }
    }
}

