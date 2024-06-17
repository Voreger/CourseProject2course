package in.reqres;

import api.data.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

public class BackendRequestTest {

    @Test
    @DisplayName("Получить список пользователей")
    public void getUsers() {
        List<UserData> users = given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("ListUserSchema.json"))
                .body("page", equalTo(2))
                .body("per_page", equalTo(6))
                .body("total", equalTo(12))
                .body("total_pages", equalTo(2))
                .extract().jsonPath().getList("data", UserData.class);

        // Проверка полученных данных
        assertThat(users).extracting(UserData::getId).isNotNull();
        assertThat(users).extracting(UserData::getFirstName).contains("Tobias");
        assertThat(users).extracting(UserData::getLastName).contains("Funke");
    }

    @Test
    @DisplayName("Проверка эндпоинта users/2")
    public void getUser() {
        UserData user = given()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("UserData.json"))
                .extract().jsonPath().getObject("data", UserData.class);

        // Проверка пользовательских данных
        assertThat(user).extracting(UserData::getId).isEqualTo(2);
        assertThat(user).extracting(UserData::getEmail).isEqualTo("janet.weaver@reqres.in");
        assertThat(user).extracting(UserData::getFirstName).isEqualTo("Janet");
        assertThat(user).extracting(UserData::getLastName).isEqualTo("Weaver");
        assertThat(user).extracting(UserData::getAvatar).isEqualTo("https://reqres.in/img/faces/2-image.jpg");
    }

    @Test
    @DisplayName("Проверка эндпоинта /users/22")
    public void getUserNotFound() {
        given()
                .when()
                .get("https://reqres.in/api/users/22")
                .then()
                .statusCode(404)
                .body(equalTo("{}"));
    }

    @Test
    @DisplayName("Проверка эндпоинта /unknown")
    public void getResources() {
        List<ColorData> resources = given()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("ColorDataList.json"))
                .body("page", equalTo(1))
                .body("per_page", equalTo(6))
                .body("total", equalTo(12))
                .body("total_pages", equalTo(2))
                .extract().jsonPath().getList("data", ColorData.class);

        // Проверка данных
        assertThat(resources).extracting(ColorData::getId).isNotNull();
        assertThat(resources).extracting(ColorData::getName).contains("cerulean");
        assertThat(resources).extracting(ColorData::getYear).contains(2000);
        assertThat(resources).extracting(ColorData::getColor).contains("#98B2D1");
    }

    @Test
    @DisplayName("Проверка эндпоинта /api/unknown/2")
    public void getResource() {
        ColorData resource = given()
                .when()
                .get("https://reqres.in/api/unknown/2")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("ColorSchema.json"))
                .extract().jsonPath().getObject("data", ColorData.class);

        // Проверка данных
        assertThat(resource).extracting(ColorData::getId).isEqualTo(2);
        assertThat(resource).extracting(ColorData::getName).isEqualTo("fuchsia rose");
        assertThat(resource).extracting(ColorData::getYear).isEqualTo(2001);
        assertThat(resource).extracting(ColorData::getColor).isEqualTo("#C74375");
        assertThat(resource).extracting(ColorData::getPantoneValue).isEqualTo("17-2031");
    }

    @Test
    @DisplayName("Проверка эндпоинта /unknown/23")
    public void getResourceNotFound() {
        given()
                .when()
                .get("https://reqres.in/api/unknown/23")
                .then()
                .statusCode(404)
                .body(equalTo("{}"));
    }

    @Test
    @DisplayName("Создание эндпоинта /users методом POST")
    public void createUser() {
        RequestUser requestUser = RequestUser.builder()
                .name("Vladimir")
                .job("Programmer")
                .build();

        ResponseUser responseUser = given()
                .contentType(ContentType.JSON)
                .body(requestUser)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("CreateUser.json"))
                .extract().as(ResponseUser.class);

        // Проверка совпадения созданного пользователя
        assertThat(responseUser).isNotNull();
        assertThat(responseUser).extracting(ResponseUser::getName).isEqualTo(requestUser.getName());
        assertThat(responseUser).extracting(ResponseUser::getJob).isEqualTo(requestUser.getJob());
    }

    @Test
    @DisplayName("Обновление пользователя методом PUT")
    public void updateUserPut() {
        RequestUser requestUser = RequestUser.builder()
                .name("Vladimir")
                .job("Programmer")
                .build();

        ResponseUser responseUser = given()
                .contentType(ContentType.JSON)
                .body(requestUser)
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("UserUpdate.json"))
                .extract().as(ResponseUser.class);

        // Проверка совпадения обновленного пользователя
        assertThat(responseUser).isNotNull();
        assertThat(responseUser).extracting(ResponseUser::getName).isEqualTo(requestUser.getName());
        assertThat(responseUser).extracting(ResponseUser::getJob).isEqualTo(requestUser.getJob());
    }

    @Test
    @DisplayName("Частичное обновление пользователя методом PATCH")
    public void updateUserPatch() {
        RequestUser requestUser = RequestUser.builder()
                .name("Vladimir")
                .job("Programmer")
                .build();

        ResponseUser responseUser = given()
                .contentType(ContentType.JSON)
                .body(requestUser)
                .when()
                .patch("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("UserUpdate.json"))
                .extract().as(ResponseUser.class);

        // Проверка совпадения частично обновленного пользователя
        assertThat(responseUser).isNotNull();
        assertThat(responseUser).extracting(ResponseUser::getName).isEqualTo(requestUser.getName());
        assertThat(responseUser).extracting(ResponseUser::getJob).isEqualTo(requestUser.getJob());
    }

    @Test
    @DisplayName("Удаление пользователя методом DELETE")
    public void deleteUser() {
        given()
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .statusCode(204)
                .body(equalTo(""));
    }

    @Test
    @DisplayName("Регистрация через эндпоинт /register")
    public void registerSuccessful() {
        RegisterLoginRequest request = RegisterLoginRequest.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();

        RegisterLoginResponse response = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SuccessfulRegisterResponse.json"))
                .extract().as(RegisterLoginResponse.class);

        // Проверка регистрации
        assertThat(response).isNotNull();
        assertThat(response).extracting(RegisterLoginResponse::getId).isEqualTo(4);
        assertThat(response).extracting(RegisterLoginResponse::getToken).isEqualTo("QpwL5tke4Pnpja7X4");
    }

    @Test
    @DisplayName("Регистрация через эндпоинт /api/register")
    public void registerUnsuccessful() {
        RegisterLoginRequest request = RegisterLoginRequest.builder()
                .email("test@gmail.ru")
                .build();

        RegisterLoginResponse response = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(400)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("LoginRegisterUnsuccessfulResponse.json"))
                .extract().as(RegisterLoginResponse.class);

        // Проверка регистрации
        assertThat(response).isNotNull();
        assertThat(response).extracting(RegisterLoginResponse::getError).isEqualTo("Missing password");
    }

    @Test
    @DisplayName("Логин через эндпоинт /login")
    public void loginSuccessful() {
        RegisterLoginRequest request = RegisterLoginRequest.builder()
                .email("eve.holt@reqres.in")
                .password("cityslicka")
                .build();

        RegisterLoginResponse response = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SuccessfulLoginResponse.json"))
                .extract().as(RegisterLoginResponse.class);

        // Проверка логина
        assertThat(response).isNotNull();
        assertThat(response).extracting(RegisterLoginResponse::getToken).isEqualTo("QpwL5tke4Pnpja7X4");
    }

    @Test
    @DisplayName("Логин через эндпоинт /login")
    public void loginUnsuccessful() {

        RegisterLoginRequest request = RegisterLoginRequest.builder()
                .email("test@gmail.ru")
                .build();

        RegisterLoginResponse response = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(400)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("LoginRegisterUnsuccessfulResponse.json"))
                .extract().as(RegisterLoginResponse.class);

        // Проверка логина
        assertThat(response).isNotNull();
        assertThat(response).extracting(RegisterLoginResponse::getError).isEqualTo("Missing password");
    }

    @Test
    @DisplayName("Получение пользователей через эндпоинт /users?delay=3 с задержкой")
    public void getUsersDelay() {
        List<UserData> users = given()
                .when()
                .get("https://reqres.in/api/users?delay=3")
                .then()
                .statusCode(200)
                .time(greaterThan(3000L)).and().time(lessThan(6000L))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("ListUserSchema.json"))
                .body("page", equalTo(1))
                .body("per_page", equalTo(6))
                .body("total", equalTo(12))
                .body("total_pages", equalTo(2))
                .extract().jsonPath().getList("data", UserData.class);

        // Проверка полученных пользователей
        assertThat(users).extracting(UserData::getId).contains(2);
        assertThat(users).extracting(UserData::getFirstName).contains("Janet");
        assertThat(users).extracting(UserData::getLastName).contains("Weaver");
    }
}
