package org.example.clothingrecommendationsystem.orchestrators.user.dto;

import lombok.Data;

@Data
public class EditUserDto {
    private String username;
    private String password;
    private String email;
}
