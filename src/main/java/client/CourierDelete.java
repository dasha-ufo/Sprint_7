package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.CourierUser;

public class CourierDelete extends RestClient {
    @Step("Отправить запрос на удаление курьера")
    public Response delete (CourierUser courierUser) {
        return getDefaultRequestSpecification()
                .body(courierUser)
                .when()
                .delete("/api/v1/courier/" + courierUser.getId());
    }
}
