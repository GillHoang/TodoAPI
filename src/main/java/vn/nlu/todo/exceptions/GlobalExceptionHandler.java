package vn.nlu.todo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tools.jackson.databind.exc.InvalidFormatException;
import vn.nlu.todo.response.ApiResponse;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleTodoNotFound(TodoNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(TodoAlreadyExisted.class)
    public ResponseEntity<ApiResponse<Object>> handleTodoAlreadyExisted(TodoAlreadyExisted ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidation(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(e -> e.getField() + ": " + e.getDefaultMessage()).toList();
        return ResponseEntity.badRequest().body(ApiResponse.error(HttpStatus.BAD_REQUEST, String.join(", ", errors)));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidEnum(HttpMessageNotReadableException ex) {
        String message = "Dữ liệu không hợp lệ";
        if (ex.getCause() instanceof InvalidFormatException ife && ife.getTargetType().isEnum()) {
            String pathRef = ife.getPathReference();
            String field = pathRef.contains("[") ? pathRef.substring(pathRef.lastIndexOf("[") + 2, pathRef.lastIndexOf("]") - 1) : pathRef;

            String validValues = Arrays.stream(ife.getTargetType().getEnumConstants()).map(Object::toString).collect(Collectors.joining(", "));

            message = String.format("Giá trị '%s' không hợp lệ cho '%s'. Hợp lệ: [%s]", ife.getValue(), field, validValues);
        }
        return ResponseEntity.badRequest().body(ApiResponse.error(HttpStatus.BAD_REQUEST, message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneral() {
        return ResponseEntity.status(500).body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Đã xảy ra lỗi hệ thống"));
    }
}