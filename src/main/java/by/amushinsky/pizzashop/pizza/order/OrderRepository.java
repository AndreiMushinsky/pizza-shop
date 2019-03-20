package by.amushinsky.pizzashop.pizza.order;

import by.amushinsky.pizzashop.pizza.design.Pizza;
import by.amushinsky.pizzashop.pizza.order.Order;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class OrderRepository {

  private SimpleJdbcInsert orderInsert;

  private SimpleJdbcInsert orderPizzaInsert;

  private ObjectMapper objectMapper;

  @Autowired
  public OrderRepository(JdbcTemplate jdbcTemplate) {
    this.orderInsert = new SimpleJdbcInsert(jdbcTemplate)
        .withTableName("Pizza_Order")
        .usingGeneratedKeyColumns("id");
    this.orderPizzaInsert = new SimpleJdbcInsert(jdbcTemplate)
        .withTableName("Pizza_Order_Pizzas");
    this.objectMapper = new ObjectMapper();
  }

  public Order save(Order order) {
    order.setPlacedAt(new Date());
    final long orderId = saveOrderDetails(order);
    order.setId(orderId);
    for (Pizza pizza : order.getPizzaList()) {
      savePizzaToOrder(pizza, orderId);
    }
    return order;
  }

  private void savePizzaToOrder(Pizza pizza, long orderId) {
    final Map<String, Object> values = new HashMap<>();
    values.put("pizzaOrder", orderId);
    values.put("pizza", pizza.getId());
    orderPizzaInsert.execute(values);
  }

  private long saveOrderDetails(Order order) {
    final Map<String, Object> values = objectMapper.convertValue(order,
        new TypeReference<Map<String, Object>>() {});
    values.put("placedAt", new Date());
    return orderInsert.executeAndReturnKey(values).longValue();
  }
}
