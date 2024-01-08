package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.CourierUser;

public class CourierCreate extends RestClient {
    @Step("Отправка запроса для создания нового курьера /api/v1/courier с заполненным телом")
    public Response create (CourierUser courierUser) {
        return getDefaultRequestSpecification()
                .body(courierUser)
                .when()
                .post("/api/v1/courier");
    }

}
