package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.Order;

public class OrderRequests extends RestClient {
    public static final String ORDERS = "/api/v1/orders";
    @Step("Отправка запроса для создания нового заказа /api/v1/orders")
    public Response createOrder (Order order) {
        return getDefaultRequestSpecification()
                .body(order)
                .when()
                .post(ORDERS);
    }

    @Step("Отправка запроса для получения списка заказов /api/v1/orders с пустым body")
    public Response receiveOrder (Order order) {
        return getDefaultRequestSpecification()
                .when()
                .get(ORDERS);
    }

}
