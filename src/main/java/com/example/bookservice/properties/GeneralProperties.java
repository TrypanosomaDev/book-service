package com.example.bookservice.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("generalprops")
@Data
public class GeneralProperties {
	private String authServiceUrl;
}
