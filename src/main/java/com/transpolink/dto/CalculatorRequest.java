package java.com.transpolink.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CalculatorRequest {

    @NotBlank(message = "Укажите город отправления")
    private String from;

    @NotBlank(message = "Укажите город назначения")
    private String to;

    @NotNull(message = "Укажите вес")
    @Positive(message = "Вес должен быть положительным")
    private Double weight;

    @NotNull(message = "Укажите объем")
    @Positive(message = "Объем должен быть положительным")
    private Double volume;

    @NotBlank(message = "Укажите ваше имя")
    private String clientName;

    @NotBlank(message = "Укажите номер телефона")
    @Pattern(regexp = "\\+?[0-9\\s()-]{7,20}", message = "Неверный формат телефона")
    private String phone;
}