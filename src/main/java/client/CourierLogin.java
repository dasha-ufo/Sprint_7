package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.CourierUser;

public class CourierLogin extends RestClient {

    @Step("Отправка запроса для авторизации курьера /api/v1/courier/login")
    public Response login (CourierUser courierUser) {
        return getDefaultRequestSpecification()
                .body(courierUser)
                .when()
                .post("/api/v1/courier/login");
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
