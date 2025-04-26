package com.ecommerce.config;

import io.minio.MinioClient;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@TestConfiguration
@Profile("test")
public class MinioMockConfig {

    @Bean
    public MinioClient configurarMinioClientTeste() {
        return Mockito.mock(MinioClient.class);
    }
}
