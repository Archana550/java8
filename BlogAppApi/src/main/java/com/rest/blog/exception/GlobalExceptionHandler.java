
package com.rest.blog.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rest.blog.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

@ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
String message = ex.getMessage();
ApiResponse apiResponse = new ApiResponse(message, false);
return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);

}

@ExceptionHandler(AccessNotPermitedException.class)
public ResponseEntity<ApiResponse> accessNotPermitedExceptionHandler(AccessNotPermitedException ex) {
String message = ex.getMessage();
ApiResponse apiResponse = new ApiResponse(message, false);
return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);

}

@ExceptionHandler(EmailAlreadyRegisteredException.class)
public ResponseEntity<ApiResponse> emailAlreadyRegisteredExceptionHandler(EmailAlreadyRegisteredException ex) {
String message = ex.getMessage();
ApiResponse apiResponse = new ApiResponse(message, false);
return new ResponseEntity<>(apiResponse, HttpStatus.ALREADY_REPORTED);

}



@ExceptionHandler(ApiException.class)
public ResponseEntity<ApiResponse> apiExceptionHandler(ApiException ex) {
String message = ex.getMessage();
ApiResponse apiResponse = new ApiResponse(message, true);
return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);

}

@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<Map<String, String>> handlerMethodArgsNotValidException(MethodArgumentNotValidException ex) {
Map<String, String> resp = new HashMap<>();
ex.getBindingResult().getAllErrors().forEach(error -> {
String fieldName = ((FieldError) error).getField();
String message = error.getDefaultMessage();
resp.put(fieldName, message);
});

return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);

}

}
