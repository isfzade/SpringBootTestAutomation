package az.isfan.automation.common.dto;

public record ErrorDto(
        Integer code,
        String type,
        String message
) {
}
