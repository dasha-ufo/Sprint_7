import client.CourierCreate;
import client.CourierDelete;
import client.CourierLogin;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.CourierUser;

import static org.hamcrest.Matchers.equalTo;


public class CreateCourierPositiveTests {
    private CourierCreate courierCreate;
    private CourierUser userForDelete;
    private CourierLogin courierLogin;

    @Before
    public void setUp() {
        courierCreate = new CourierCreate();
        userForDelete = new CourierUser();
    }

    @After
    public void cleanUp() {
        CourierLogin courierLogin = new CourierLogin();

        Response apiLogin = courierLogin.login(userForDelete);
        String id = CourierLogin.receiveId(apiLogin);

        CourierUser requestForDelete = new CourierUser(id);
        CourierDelete courierDelete = new CourierDelete();


        Response apiDelete = courierDelete.delete(requestForDelete);
        checkStatusOfDelete200(apiDelete);
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Создаем курьера со всеми заполненными полями")
    public void CourierCreationSuccess () {
        CourierUser request = new CourierUser();

        request.setLogin(CourierUser.randomLogin());
        request.setPassword(CourierUser.randomPassword());
        request.setFirstname(CourierUser.addFirstname());
        userForDelete = request;

        Response apiResponse = courierCreate.create(request);
        checkStatusOfCreationIs201(apiResponse);
    }

    @Test
    @DisplayName("Создание курьера только с обязательными полями")
    @Description("Создаем курьера только с заполнением обязательных полей: логин и пароль")
    public void CourierCreationSuccessOnlyNecessaryFields () {

        CourierUser request = new CourierUser();
        request.setLogin(CourierUser.randomLogin());
        request.setPassword(CourierUser.randomPassword());

        userForDelete = request;

        Response apiResponse = courierCreate.create(request);
        checkStatusOfCreationIs201(apiResponse);
    }

    @Step("Проверяем, что статус ответа 201 и тело ответа содержит ок со значением true")
    public void checkStatusOfCreationIs201(Response apiResponse){
        apiResponse.then().body("ok", equalTo(true))
                .and()
                .statusCode(201);
    }

    @Step("Проверяем, что статус ответа 200 и тело ответа содержит ок со значением true")
    public void checkStatusOfDelete200(Response apiResponse){
        apiResponse.then().body("ok", equalTo(true))
                .and()
                .statusCode(200);
    }

}
