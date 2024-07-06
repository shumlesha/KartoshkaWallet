package ru.cft.template.util;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import ru.cft.template.dto.api.DefaultResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@UtilityClass
public class DefaultResponseBuilder {

    public static <T> DefaultResponse<T> success(String message, T data) {
        return DefaultResponse.<T>builder()
                .status(HttpStatus.OK)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> DefaultResponse<T> success(String message) {
        return success(message, null);
    }

    public static <T> DefaultResponse<T> error(String message, HttpStatus status, Map<String, List<String>> errors) {
        return DefaultResponse.<T>builder()
                .status(status)
                .message(message)
                .data(null)
                .errors(errors)
                .build();
    }

    public static <T> DefaultResponse<T> error(String message, HttpStatus status) {
        return error(message, status, null);
    }

}
