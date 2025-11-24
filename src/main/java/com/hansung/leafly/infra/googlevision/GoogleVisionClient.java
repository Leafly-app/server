package com.hansung.leafly.infra.googlevision;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hansung.leafly.infra.googlevision.exception.GoogleVisionRequestFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.util.Base64;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class GoogleVisionClient {

    @Value("${google.vision.api.key}")
    private String apiKey;

    @Value("${google.vision.endpoint}")
    private String endpoint;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * 이미지 OCR 수행
     */
    public String detectText(MultipartFile file) {
        try {
            // 이미지 Base64 인코딩
            String base64Image = Base64.getEncoder().encodeToString(file.getBytes());

            // Vision API 요청 바디
            Map<String, Object> requestBody = Map.of(
                    "requests", List.of(
                            Map.of(
                                    "image", Map.of("content", base64Image),
                                    "features", List.of(
                                            Map.of("type", "TEXT_DETECTION")
                                    )
                            )
                    )
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<?> entity = new HttpEntity<>(requestBody, headers);

            String url = endpoint + "?key=" + apiKey;

            String response = restTemplate.postForObject(url, entity, String.class);

            // JSON 파싱해서 OCR 텍스트만 추출
            JsonNode root = mapper.readTree(response);
            JsonNode textNode = root
                    .path("responses")
                    .get(0)
                    .path("fullTextAnnotation")
                    .path("text");

            return textNode.asText();

        } catch (Exception e) {
            log.error("Google Vision API 호출 실패: {}", e.getMessage(), e);
            throw new GoogleVisionRequestFailedException();
        }
    }
}
