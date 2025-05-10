package com.ecommerce;

import com.ecommerce.config.MinioTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(MinioTestConfig.class)
class ComercioApplicationTests {

    @Test
    void contextLoads() {
    }

}
