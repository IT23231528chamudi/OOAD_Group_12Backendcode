package com.example.splendorlux.config;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PayPalConfig {

    @Value("${paypal.client.id}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String clientSecret;

    @Value("${paypal.mode}")
    private String mode; // sandbox or live

    @Bean
    public APIContext apiContext() throws PayPalRESTException {
        // Set up PayPal OAuth credentials and API context
        Map<String, String> sdkConfig = new HashMap<>();
        sdkConfig.put("mode", mode); // Configure mode as sandbox or live

        OAuthTokenCredential oAuthTokenCredential = new OAuthTokenCredential(clientId, clientSecret, sdkConfig);
        APIContext apiContext = new APIContext(oAuthTokenCredential.getAccessToken());
        apiContext.setConfigurationMap(sdkConfig);

        return apiContext;
    }
}
