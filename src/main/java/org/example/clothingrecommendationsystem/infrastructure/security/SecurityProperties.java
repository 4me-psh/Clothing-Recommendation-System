package org.example.clothingrecommendationsystem.infrastructure.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class SecurityProperties {
    private String secret;
    private Long expiration;
    private String pathForRegisterUser;
    private String registrationToken;
}
