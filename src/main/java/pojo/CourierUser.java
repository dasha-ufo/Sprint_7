package pojo;

import client.CourierDelete;
import client.CourierLogin;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import io.qameta.allure.Step;

import java.io.Reader;

import static org.hamcrest.Matchers.equalTo;


public class CourierUser {

    public String randomPassword;
    private String login;
    private String password;
    private String firstname;
    private String id;


    public CourierUser(String login, String password, String firstname) {
        this.login = login;
        this.password = password;
        this.firstname = firstname;
    }

    public CourierUser(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public CourierUser(String id) {
        this.id = id;
    }

    @Step("Создаем рандомный пароль из 4 цифр")
    public static String randomPassword() {
        return RandomStringUtils.randomNumeric(4);
    }

    @Step("Создаем рандомный логин из 6 букв")
    public static String randomLogin() {
        return RandomStringUtils.randomAlphabetic(6);
    }

    @Step("Создаем рандомный логин firstname из 5 букв")
    public static String addFirstname() {
        return RandomStringUtils.randomAlphabetic(5);
    }


    public CourierUser() {}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getId() {
        return id;
    }

    public void setId(String login) {
        this.id = id;
    }

    @Step("Отправляем запрос на удаление курьера")
    public static void deleteOfCourier(CourierUser userForDelete) {
        CourierLogin courierLogin = new CourierLogin();

        Response apiLogin = courierLogin.login(userForDelete);
        String id = CourierLogin.receiveId(apiLogin);

        CourierUser requestForDelete = new CourierUser(id);
        CourierDelete courierDelete = new CourierDelete();

        Response apiDelete = courierDelete.delete(requestForDelete);
    }

    @Step("Отправляем запрос на удаление курьера и проверяем успешный ответ ОК и статус 200")
    public static void deleteOfCourierWithStatusCheck(CourierUser userForDelete) {
        CourierLogin courierLogin = new CourierLogin();

        Response apiLogin = courierLogin.login(userForDelete);
        String id = CourierLogin.receiveId(apiLogin);

        CourierUser requestForDelete = new CourierUser(id);
        CourierDelete courierDelete = new CourierDelete();

        Response apiDelete = courierDelete.delete(requestForDelete);
        apiDelete.then().body("ok", equalTo(true))
                .and()
                .statusCode(200);
    }

    }
