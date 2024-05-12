package com.picpaychallenge.user.integration;

import com.picpaychallenge.common.domain.model.valueobjects.cnpj.CNPJConverter;
import com.picpaychallenge.common.domain.model.valueobjects.cpf.CPFConverter;
import com.picpaychallenge.common.domain.model.valueobjects.document.Document;
import com.picpaychallenge.user.factory.UserFactory;
import com.picpaychallenge.user.payload.UserDTO;
import com.picpaychallenge.user.payload.UserForm;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTest {
        private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
                "postgres:latest"
        );
        private final RestTemplate restTemplate = new RestTemplate();
        private final CPFConverter cpfConverter = new CPFConverter();
        private final CNPJConverter cnpjConverter = new CNPJConverter();
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

        @Test
        void shouldPostClinic() {
            UserDTO userDTO = UserFactory.getUserDTOForPost();
            given()
                    .contentType(ContentType.JSON)
                    .body(UserFactory.getUserFormForPost())
                    .when()
                    .post("http://localhost:" + port + "/api/v1/users")
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("typeUser", equalTo(userDTO.getTypeUser().toString()))
                    .body("document.value", equalTo(cnpjConverter.convertToDatabaseColumn(userDTO.getDocument())))
                    .body("email", equalTo(userDTO.getEmail()));
        }
}
