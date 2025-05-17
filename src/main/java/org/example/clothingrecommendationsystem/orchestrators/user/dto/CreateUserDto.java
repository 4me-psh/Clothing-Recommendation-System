package org.example.clothingrecommendationsystem.orchestrators.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "Create User",
        description = "Schema to hold information about user to be created"
)
public class CreateUserDto {
    private String username;
    private String password;
    private String email;
}
