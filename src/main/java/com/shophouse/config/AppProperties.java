package com.shophouse.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.properties")
public class AppProperties {
    private String uploadPath;
    private String appUrl;
    private String JwtSecret;
    private long JwtExpiration;
    private String adminCreationKey;
}
