package com.ecommerce.config;

import io.minio.MinioClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.testcontainers.containers.MinIOContainer;

@TestConfiguration
@Profile("test")
public class MinioTestConfig {

    private static final MinIOContainer minioContainer;

    static {
        minioContainer = new MinIOContainer("minio/minio:RELEASE.2023-09-04T19-57-37Z")
                .withUserName("minioadmin")
                .withPassword("minioadmin");

        minioContainer.start();
    }

    @Bean
    public MinioClient configurarMinioClientTeste() {
        return MinioClient
                .builder()
                .endpoint(minioContainer.getS3URL())
                .credentials(minioContainer.getUserName(), minioContainer.getPassword())
                .build();
    }
}

