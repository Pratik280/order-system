package com.pratik.microservices.order_system.common.exception;

import com.pratik.microservices.order_system.common.response.BaseResponse;
import com.pratik.microservices.order_system.common.util.ResponseBuilder;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleResourceNotfound(
        ResourceNotFoundException exception, HttpServletRequest request ){

//        Map<String, Object> errors = new HashMap<>();
//        errors.put("message", exception.getMessage());
//        errors.put("errorCode", "RESOURCE_NOT_FOUND");
//        errors.put("path", request.getRequestURI());

        return ResponseBuilder.error(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                List.of(Map.of(
                        "code", "RESOUCE_NOT_DFOUND",
                        "massage", exception.getMessage(),
                        "path", request.getRequestURI()
                ))
        );
    }


    // Resource not found 404
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<BaseResponse<Object>> handleNotFound(
//            ResourceNotFoundException exception, HttpServletRequest request){
//
//        return ResponseBuilder.error(
//                HttpStatus.NOT_FOUND,
//                exception.getMessage(),
//                List.of(errorItem("RESOURCE_NOT_FOUND", exception.getMessage(), request))
//        );
//    }

    // handleNotReadable Exception
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<Object>> handleNotReadable(HttpMessageNotReadableException exception, HttpServletRequest request){
        return ResponseBuilder.error(
                HttpStatus.BAD_REQUEST,
                "Request body is missing or malformed.",
                List.of(errorItem("INVALID_REQUEST_BODY",
                        "Request body is missing or malformed.",
                        request))
        );
    }

    // handleNotReadable Exception
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BaseResponse<Object>> handleBusiness(BusinessException exception, HttpServletRequest request){
        return ResponseBuilder.error(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                List.of(errorItem(exception.getErrorCode(), exception.getMessage(), request))
        );
    }

    // Validation Exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Object>> handleValidation(
            MethodArgumentNotValidException exception, HttpServletRequest request){
        return ResponseBuilder.error(
                HttpStatus.BAD_REQUEST,
                "Validation Failed",
                List.of(errorItem("VALIDATION_FAILED", exception.getBindingResult().getFieldError().getDefaultMessage(), request))
        );
    }

    // Generic Exception 500
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponse<Object>> handleAll(Exception exception, HttpServletRequest request){
        return ResponseBuilder.error(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Unexpected Internal Error",
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

//    public static Map<String, Object> buildErrors(String message, String errorCode, HttpServletRequest request){
//        Map<String, Object> errors = new HashMap<>();
//        errors.put("message", message);
//        errors.put("code", errorCode);
//        errors.put("path", request.getRequestURI());
//        return  errors;
//    }
}
