package org.example.clothingrecommendationsystem.infrastructure.external;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="external")
@Data
public class ExternalConfig {
    private String pythonPath;
    private String clothingPhotosPath;
    private String generatedImagesPath;
    private String removedBGPhotosPath;
    private String weatherApiKey;
    private String weatherApiUrl;
}
