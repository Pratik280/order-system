package com.pratik.microservices.order_system.common.exception;

import com.pratik.microservices.order_system.common.response.BaseResponse;
import com.pratik.microservices.order_system.common.util.ResponseBuilder;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Resource not found 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleNotFound(
            ResourceNotFoundException exception, HttpServletRequest request){

        return ResponseBuilder.error(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                List.of(errorItem("RESOURCE_NOT_FOUND", exception.getMessage(), request))
        );
    }


    // Generic Exception 500

    public ResponseEntity<BaseResponse<Object>> handleAll(Exception exception, HttpServletRequest request){
        return ResponseBuilder.error(
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                List.of(errorItem("INTERNAL_ERROR", exception.getMessage(), request))
        );
    }

    // error item
    private Map<String, Object> errorItem(String code, String message, HttpServletRequest request){
        return Map.of(
                "code", code,
                "message", message,
                "path", request.getRequestURI()
        );
    }
}
