package ru.efimkin.bredik.authservice.dto;

import lombok.Data;

@Data
public class JwtAuthDto {
    private String token;
    private String refreshToken;
}
