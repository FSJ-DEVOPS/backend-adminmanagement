package com.kce.admin.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Configuration
public class FeignConfig {

    private static final Logger logger = LoggerFactory.getLogger(FeignConfig.class);

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                try {
                    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                    if(attributes != null) {
                        HttpServletRequest request = attributes.getRequest();
                        String authHeader = request.getHeader("Authorization");
                        if(authHeader != null && authHeader.startsWith("Bearer ")) {
                            requestTemplate.header("Authorization", authHeader);
                            logger.info("Forwarding Authorization header to Feign client..");
                        } else {
                            logger.warn("No valid Authorization header found in request");
                        }
                    } else {
                        logger.warn("No ServeltRequestAttributes found in request");
                    }
                } catch(Exception e) {
                    logger.error("Error in Feign RequestInterceptor: ", e);
                }
            }
        };
    }
}
