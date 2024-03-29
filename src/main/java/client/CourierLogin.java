package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.CourierUser;

public class CourierLogin extends RestClient {

    public static final String COURIER_LOGIN = "/api/v1/courier/login";


    @Step("Отправка запроса для авторизации курьера /api/v1/courier/login")
    public Response login (CourierUser courierUser) {
        return getDefaultRequestSpecification()
                .body(courierUser)
                .when()
                .post(COURIER_LOGIN);
    }

    @Step("Вытащить из ответа id курьера")
    public static String receiveId (Response response) {
        String id = response.then()
                .extract()
                .jsonPath()
                .getString("id");
        return id;
    }
}
