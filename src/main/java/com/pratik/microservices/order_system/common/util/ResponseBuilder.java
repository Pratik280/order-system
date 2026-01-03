package com.pratik.microservices.order_system.common.util;

import com.pratik.microservices.order_system.common.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ResponseBuilder {
    public static <T> ResponseEntity<BaseResponse<T>> success(
            HttpStatus status, String message, T data ){
        return ResponseEntity.status(status)
                .body(BaseResponse.<T>builder()
                                .status(status.value())
                                .message(message)
                                .data(data)
                                .build());
    }

    public static <T> ResponseEntity<BaseResponse<T>> error(
            HttpStatus status, String message, List<Map<String, Object>> errors ){
        return ResponseEntity.status(status)
                .body(BaseResponse.<T>builder()
                        .status(status.value())
                        .message(message)
                        .errors(errors)
                        .build());
    }
}
