package com.store.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Register {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
