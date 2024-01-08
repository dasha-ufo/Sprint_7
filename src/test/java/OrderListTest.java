import client.OrderRequests;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import pojo.Order;

import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest {


    OrderRequests orderCreate;

    @Before
    public void setUp() {
        orderCreate = new OrderRequests();
    }



    @Test
    @DisplayName("Получить список заказов")
    @Description("Получаем список заказов без параметров в body по дефолтному запросу /api/v1/orders")
    public void ReceiveOrdersList() {
        Order order = new Order();
        Response apiResponse = orderCreate.receiveOrder(order);
        checkResponseWithOrdersNotEmpty(apiResponse);
    }

    @Step("Проверяем, что статус поиска заказов 200 и тело содержит данные")
    public void checkResponseWithOrdersNotEmpty(Response apiResponse) {
            apiResponse.then().body(notNullValue())
            .and()
                .statusCode(200);
    }
}
