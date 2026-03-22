package com.pratik.microservices.order_system.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JsonUtil {

        private final ObjectMapper objectMapper;

        public String convertToJson(Object obj) {
            try {
                return objectMapper.writeValueAsString(obj);
            } catch (Exception e) {
                throw new RuntimeException("Error converting to JSON", e);
            }
        }
}
