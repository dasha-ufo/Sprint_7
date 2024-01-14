import client.OrderRequests;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pojo.Order;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreateTests {

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final Integer rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;

    public OrderCreateTests (String firstName, String lastName, String address, String metroStation, String phone, Integer rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] setDifferentColour() {
        return new Object[][]{
                {"Petr", "Petrov", "Mosselmash 59, kv 11", "1","8 800 555 3555", 6, "2024-01-01", "Hello, how are you",new String[] {"BLACK"}},
        {"Petr", "Petrov", "Mosselmash 59, kv 11", "2","7 700 555 3555", 6, "2024-03-01", "Piu piu piu", new String[] {"BLACK", "GREY"}},
                {"Agnessa", "Ivanova", "Tsvetochnaya 1, kv 2", "3","8 800 555 3555", 11, "2024-02-01", "Hello, how are you", new String[0]},
        };
    }

    OrderRequests orderCreate;

    @Before
    public void setUp() {
        orderCreate = new OrderRequests();
    }


  @Test
  @DisplayName("Создание заказа с разными наборами данных")
  @Description("Создаем три типа заказов: с указанием одного цвета, с указанием двух цветов, без указания цветов")
    public void createOrder() {
    Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    Response apiResponse = orderCreate.createOrder(order);
      checkSuccessResponceForCreateOrder(apiResponse);
}


    @Step("Проверяем успешный ответ с кодом 201 и track заказа в теле ответа")
    public void checkSuccessResponceForCreateOrder(Response apiResponse){
      apiResponse.then().body("track", notNullValue())
            .and()
              .statusCode(201);
    }
}

