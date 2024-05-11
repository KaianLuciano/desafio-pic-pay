package com.picpaychallenge.user.integration;

import com.picpaychallenge.common.domain.model.valueobjects.document.Document;
import io.restassured.RestAssured;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTest {
        private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
                "postgres:latest"
        );
        private final RestTemplate restTemplate = new RestTemplate();
        private final Document document = new Document();
        @LocalServerPort
        private Integer port;

        @BeforeAll
        static void beforeAll() {
            postgres.start();
        }

        @AfterAll
        static void afterAll() {
            postgres.stop();
        }

        @DynamicPropertySource
        static void configureProperties(DynamicPropertyRegistry registry) {
            registry.add("spring.datasource.url", postgres::getJdbcUrl);
            registry.add("spring.datasource.username", postgres::getUsername);
            registry.add("spring.datasource.password", postgres::getPassword);
        }

        @BeforeEach
        @Transactional
        void setUp() {
            RestAssured.baseURI = "http://localhost:" + port;
        }

        @Test
        void connectionEstablished() {
            assertThat(postgres.isCreated()).isTrue();
            assertThat(postgres.isRunning()).isTrue();
        }
}
