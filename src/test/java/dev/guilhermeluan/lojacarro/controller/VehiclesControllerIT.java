package dev.guilhermeluan.lojacarro.controller;

import dev.guilhermeluan.lojacarro.commons.FileUtils;
import dev.guilhermeluan.lojacarro.config.IntegrationTestConfig;
import dev.guilhermeluan.lojacarro.repositories.VehiclesRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import net.javacrumbs.jsonunit.core.Option;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VehiclesControllerIT extends IntegrationTestConfig {
    private static final String URL = "/v1/vehicles";

    @LocalServerPort
    private int port;

    @Autowired
    private FileUtils fileUtils;
    @Autowired
    private VehiclesRepository repository;

    @BeforeEach
    void setUrl() {
        RestAssured.baseURI = "http://localhost:" + port;
        RestAssured.port = port;
    }

    @Test
    @DisplayName("GET /v1/vehicles/{id} returns vehicle with given id")
    @Sql(value = "/vehicles/sql/init_one_vehicle.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/vehicles/sql/clean_vehicle.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Order(1)
    void findById_ReturnsVehicle_WithGivenId() {
        var expectedResponse = fileUtils.readResourceFile("vehicles/get/get-vehicle-by-id-200.json");
        Long id = repository.findAll().get(0).getId();

        Assertions.assertThat(id).isNotNull();

        RestAssured.given()
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .when()
                .get(URL + "/{id}", id)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(Matchers.equalTo(expectedResponse))
                .log().all();
    }

    @Test
    @DisplayName("GET /v1/vehicles/{id} throws bad request exception when vehicle is not found")
    @Sql(value = "/vehicles/sql/init_one_vehicle.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/vehicles/sql/clean_vehicle.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Order(2)
    void findById_ThrowsBadRequestException_WhenVehicleIsNotFound() {
        var expectedResponse = fileUtils.readResourceFile("vehicles/get/get-vehicle-by-id-404.json");
        Long id = 99L;

        RestAssured.given()
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .when()
                .get(URL + "/{id}", id)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body(Matchers.equalTo(expectedResponse))
                .log().all();
    }

    @Test
    @DisplayName("POST /v1/vehicles creates vehicles when successful")
    @Sql(value = "/vehicles/sql/clean_vehicle.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Order(3)
    void save_CreatesVehicle_WhenSuccessful() {
        var request = fileUtils.readResourceFile("vehicles/post/post-vehicle-200-request.json");
        var expectedResponse = fileUtils.readResourceFile("vehicles/post/post-vehicle-200-response.json");

        String response = RestAssured.given()
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request)
                .when()
                .post(URL)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .log().all()
                .extract().body().asString();

        JsonAssertions.assertThatJson(response)
                .node("id")
                .asNumber().isPositive();


        JsonAssertions.assertThatJson(response)
                .whenIgnoringPaths("id")
                .when(Option.IGNORING_ARRAY_ORDER)
                .isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("DELETE /v1/vehicles/{id} removes vehicle with given id")
    @Sql(value = "/vehicles/sql/init_one_vehicle.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/vehicles/sql/clean_vehicle.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Order(4)
    void delete_RemovesVehicle_WithGivenId() {
        Long id = repository.findAll().get(0).getId();
        Assertions.assertThat(id).isNotNull();

        RestAssured.given()
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .when()
                .delete(URL + "/{id}", id)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .log().all();
    }

    @Test
    @DisplayName("DELETE /v1/vehicles/{id} throws Not Found exception when vehicle is not found")
    @Sql(value = "/vehicles/sql/init_one_vehicle.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/vehicles/sql/clean_vehicle.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Order(5)
    void delete_ThrowsNotFoundException_WhenVehicleIsNotFound() {
        var expectedResponse = fileUtils.readResourceFile("vehicles/delete/delete-vehicle-by-id-404.json");
        Long id = 99L;

        RestAssured.given()
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .when()
                .delete(URL + "/{id}", id)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body(Matchers.equalTo(expectedResponse))
                .log().all();

    }

    @Test
    @DisplayName("PUT /v1/vehicles updates vehicles when successful")
    @Sql(value = "/vehicles/sql/init_one_vehicle.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/vehicles/sql/clean_vehicle.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Order(6)
    void update_UpdatesVehicle_WhenSuccessful() {
        var request = fileUtils.readResourceFile("vehicles/put/put-vehicle-200-request.json");
        Long id = repository.findAll().get(0).getId();

        Assertions.assertThat(id).isNotNull();

        request = request.replace("1", id.toString());

        RestAssured.given()
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request)
                .when()
                .put(URL)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .log().all();
    }
}