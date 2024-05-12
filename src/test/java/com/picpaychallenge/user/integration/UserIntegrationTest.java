package com.picpaychallenge.user.integration;

import com.picpaychallenge.common.domain.model.valueobjects.cnpj.CNPJConverter;
import com.picpaychallenge.common.domain.model.valueobjects.cpf.CPFConverter;
import com.picpaychallenge.common.domain.model.valueobjects.document.Document;
import com.picpaychallenge.common.domain.utils.JsonConverter;
import com.picpaychallenge.user.UserService;
import com.picpaychallenge.user.factory.UserFactory;
import com.picpaychallenge.user.payload.UserDTO;
import com.picpaychallenge.user.payload.UserRepository;
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
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private UserService userService;
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
            userRepository.deleteAll();

            RestAssured.baseURI = "http://localhost:" + port;
        }

        @Test
        void connectionEstablished() {
            assertThat(postgres.isCreated()).isTrue();
            assertThat(postgres.isRunning()).isTrue();
        }

        @Test
        void testPostUser() {
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

        @Test
        void testPutUser() {
            UserDTO userDTO = UserFactory.getUserDTOForPut();
            UserDTO userSaved = userService.create(UserFactory.getUserFormForPost());
            given()
                    .contentType(ContentType.JSON)
                    .body(UserFactory.getUserFormForPut())
                    .when()
                    .put("http://localhost:" + port + "/api/v1/users/" + userSaved.getIdUser())
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.OK.value())
                    .body("typeUser", equalTo(userDTO.getTypeUser().toString()))
                    .body("document.value", equalTo(cnpjConverter.convertToDatabaseColumn(userDTO.getDocument())))
                    .body("email", equalTo(userDTO.getEmail()));
        }

        @Test
        void testPutNotFoundUser() {
            given()
                    .contentType(ContentType.JSON)
                    .body(UserFactory.getUserFormForPut())
                    .when()
                    .put("http://localhost:" + port + "/api/v1/users/" + 999999999)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        void testGetAllUsers() {
            UserDTO userDTO = UserFactory.getUserDTOForPost();
            UserDTO userSaved = userService.create(UserFactory.getUserFormForPost());
            userDTO.setIdUser(userSaved.getIdUser());
            String userExpected = JsonConverter.asJson(new PageImpl<>(
                    List.of(userDTO),
                    PageRequest.of(0, 10),
                    1)
            );

            given()
                    .contentType(ContentType.JSON)
                    .when()
                    .get("http://localhost:" + port + "/api/v1/users")
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.OK.value())
                    .body(equalTo(userExpected));
        }
}
