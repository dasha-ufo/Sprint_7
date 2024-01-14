import client.OrderRequests;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import pojo.Order;


public class OrderListTest {


    OrderRequests orderCreate;

    @Before
    public void setUp() {
        orderCreate = new OrderRequests();
    }



    @Test
    @DisplayName("Получить список заказов")
    @Description("Получаем список заказов без параметров в body по дефолтному запросу /api/v1/orders")
    public void receiveOrdersList() {
        Order order = new Order();
        Response apiResponse = orderCreate.receiveOrder(order);
        checkOrdersListIsNotEmpty(apiResponse);
    }

    @Step("Проверяем, что статус поиска заказов 200 и список заказов не пустой")
    public void checkOrdersListIsNotEmpty(Response apiResponse) {
            apiResponse.then().body("orders", Matchers.not(Matchers.empty()))
            .and()
                .statusCode(200);
    }
}
