package ru.cft.template.dto.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultResponse<T> { // Убрал statusCode, timestamp
    private HttpStatus status;
    private String message;
    private T data;
    private Map<String, List<String>> errors;
}
