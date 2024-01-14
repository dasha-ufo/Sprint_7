package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.CourierUser;

import static client.CourierCreate.COURIER_CREATE;

public class CourierDelete extends RestClient {
    @Step("Отправить запрос на удаление курьера")
    public Response delete (CourierUser courierUser) {
        return getDefaultRequestSpecification()
                .body(courierUser)
                .when()
                .delete(COURIER_CREATE + "/" + courierUser.getId());
    }
}
