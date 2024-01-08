import client.CourierCreate;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import pojo.CourierUser;

import static org.hamcrest.Matchers.equalTo;

public class CourierCreateNegativeTests {
    private CourierCreate courierCreate;

    @Before
    public void setUp() {
        courierCreate = new CourierCreate();
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Создаем курьера без заполнения обязательного поля логин")
    public void CourierWithoutLogin () {
        String password = CourierUser.randomPassword();

        CourierUser request = new CourierUser();
        request.setPassword(password);

        Response apiResponse = courierCreate.create(request);
        checkStatusOfCreationIs400(apiResponse);
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Создаем курьера без заполнения обязательного поля пароль")
    public void CourierWithoutPassword () {
        String login = CourierUser.randomLogin();

        CourierUser request = new CourierUser();
        request.setLogin(login);

        Response apiResponse = courierCreate.create(request);
        checkStatusOfCreationIs400(apiResponse);
    }


    @Test
    @DisplayName("Создание курьера с существующим логином")
    @Description("Создаем курьера с логином, который уже зарегистрирован в системе")
    public void DoubleCourierCreationError409 () {
        String login = CourierUser.randomLogin();
        String password = CourierUser.randomPassword();

        CourierUser request = new CourierUser();
        request.setLogin(login);
        request.setPassword(password);

        Response apiResponse = courierCreate.create(request);
        Response apiResponseError = courierCreate.create(request);
        checkStatusOfCreationIs409(apiResponseError);
    }


    @Step("Проверяем, что статус ответа 400 и тело ответа ошибку с текстом: Недостаточно данных для создания учетной записи")
    public void checkStatusOfCreationIs400(Response apiResponse){
        apiResponse.then().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Step("Проверяем, что статус ответа 409 и тело ответа ошибку с текстом: Этот логин уже используется")
    public void checkStatusOfCreationIs409(Response apiResponse){
        apiResponse.then().body("message", equalTo("Этот логин уже используется"))
                .and().statusCode(409);
    }

}
