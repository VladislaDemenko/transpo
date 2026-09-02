package java.com.transpolink.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ConsultationRequest {

    @NotBlank(message = "Укажите ваше имя")
    private String clientName;

    @NotBlank(message = "Укажите номер телефона")
    @Pattern(regexp = "\\+?[0-9\\s()-]{7,20}", message = "Неверный формат телефона")
    private String phone;

    @NotBlank(message = "Укажите email")
    @Email(message = "Неверный формат email")
    private String email;
}