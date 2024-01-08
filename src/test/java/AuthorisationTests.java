import client.CourierCreate;
import client.CourierLogin;
import com.google.gson.Gson;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import pojo.CourierUser;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class AuthorisationTests {

    Gson gson = new Gson();
    private CourierCreate courierCreate;
    private CourierLogin courierLogin;

    @Before
    public void setUp() {
        courierCreate = new CourierCreate();
        courierLogin = new CourierLogin();
    }


    @Test
    @DisplayName("Успешная авторизация")
    @Description("Успешная авторизация с указанием верного логина и пароля")
    public void CourierAuthSuccess () {
        String login = RandomStringUtils.randomAlphabetic(6);
        String password = CourierUser.randomPassword();

        CourierUser request = new CourierUser();
        request.setLogin(login);
        request.setPassword(password);

        Response createCourier = courierCreate.create(request);
        Response apiResponse = courierLogin.login(request);
        checkStatusAndBodyOfSuccessLogin(apiResponse);
    }

    @Test
    @DisplayName("Авторизация без регистрации курьера")
    @Description("Авторизация под логином и паролем, который ранее не был зарегистрирован")
    public void CourierAuthNonExist () {
        String login = RandomStringUtils.randomAlphabetic(6);
        String password = CourierUser.randomPassword();

        CourierUser request = new CourierUser();
        request.setLogin(login);
        request.setPassword(password);

        Response apiResponse = courierLogin.login(request);
        checkAuthorisationWithWrongFields(apiResponse);
    }

    @Test
    @DisplayName("Авторизация с неправильным паролем")
    @Description("Авторизация с указанием неправильного пароля")
    public void CourierAuthWrongPassword () {
        String login = RandomStringUtils.randomAlphabetic(6);
        String password = CourierUser.randomPassword();

        CourierUser request = new CourierUser();
        request.setLogin(login);
        request.setPassword(password);

        Response createCourier = courierCreate.create(request);

        CourierUser requestForLogin = new CourierUser();
        requestForLogin.setLogin(login);
        requestForLogin.setPassword("00000");

        Response apiResponse = courierLogin.login(requestForLogin);
        checkAuthorisationWithWrongFields(apiResponse);
    }

    @Test
    @DisplayName("Авторизация без пароля")
    @Description("Авторизация курьера без указания пароля")
    public void CourierAuthWithoutPassword () {
        String login = RandomStringUtils.randomAlphabetic(6);

        CourierUser request = new CourierUser();
        request.setLogin(login);

        Response apiResponse = courierLogin.login(request);
        checkAuthorisationWithoutPasswOrLogin(apiResponse);
    }

    @Test
    @DisplayName("Авторизация без логина")
    @Description("Авторизация курьера без указания логина")
    public void CourierAuthWithoutLogin () {
        String password = CourierUser.randomPassword();

        CourierUser request = new CourierUser();
        request.setPassword(password);

        Response apiResponse = courierLogin.login(request);
        checkAuthorisationWithoutPasswOrLogin(apiResponse);
    }


    @Step("Проверяем успешный логин со статусом ответа 200 и заполненным body")
    public void checkStatusAndBodyOfSuccessLogin(Response apiResponse){
        apiResponse.then().body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @Step("Проверяем, что при авторизации с неправильным логином/паролем возвращается ошибка 404 с текстом: Учетная запись не найдена")
    public void checkAuthorisationWithWrongFields(Response apiResponse){
            apiResponse.then().body("message", equalTo("Учетная запись не найдена"))
            .and()
             .statusCode(404);
    }

   @Step("Проверяем, что при авторизации без указания логина/пароля возвращается ошибка 400 с текстом: Недостаточно данных для входа")
   public void checkAuthorisationWithoutPasswOrLogin(Response apiResponse){
            apiResponse.then().body("message", equalTo("Недостаточно данных для входа"))
                    .and()
                    .statusCode(400);

        }
}
