package by.amushinsky.pizzashop.pizza.design;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;


@Repository
public class PizzaRepository {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public PizzaRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public Pizza save(Pizza pizza) {
    long pizzaId = savePizzaInfo(pizza);
    pizza.setId(pizzaId);
    for (String ingredientId : pizza.getIngredients()) {
      saveIngredientToPizza(ingredientId, pizza);
    }
    return pizza;
  }

  private long savePizzaInfo(Pizza pizza) {
    pizza.setCreatedAt(new Date());
    final PreparedStatementCreatorFactory creatorFactory = new PreparedStatementCreatorFactory(
        "insert into Pizza (name, createdAt) values (?, ?)",
        Types.VARCHAR, Types.TIMESTAMP);
    creatorFactory.setReturnGeneratedKeys(true);
    final PreparedStatementCreator creator =
        creatorFactory.newPreparedStatementCreator(Arrays.asList(
            pizza.getName(), new Timestamp(pizza.getCreatedAt().getTime())));
    final KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(creator, keyHolder);
    return keyHolder.getKey().longValue();
  }

  private void saveIngredientToPizza(String ingredientId, Pizza pizza) {
    jdbcTemplate.update(
        "insert into Pizza_Ingredients (pizza, ingredient) values (?, ?)",
        pizza.getId(), ingredientId);
  }

}
