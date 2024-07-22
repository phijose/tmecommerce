package com.tigmaminds.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO<T> {
    private T message;
    private String requestedPath;
    private LocalDateTime timestamp;

    public ResponseDTO(T message, String requestedPath) {
        this.message = message;
        this.requestedPath = requestedPath;
        this.timestamp = LocalDateTime.now();
    }
}
