package com.hospital.exception;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ApiError {
    private HttpStatus status;
    private String message;
    private LocalDateTime timestamp;
} 