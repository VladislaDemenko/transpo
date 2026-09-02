package com.transpolink.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DirectRequest {

    @NotBlank(message = "Укажите ваше ФИО")
    private String fullName;

    @NotBlank(message = "Укажите email")
    @Email(message = "Неверный формат email")
    private String email;

    private String organization;

    @NotBlank(message = "Выберите тип услуги")
    private String serviceType;

    @NotBlank(message = "Опишите ваш груз и требования")
    private String message;
}