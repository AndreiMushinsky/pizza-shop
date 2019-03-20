package by.amushinsky.pizzashop.pizza.design;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class IngredientRepository {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public IngredientRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public Iterable<Ingredient> findAll() {
    return jdbcTemplate.query("select id, name, type from Ingredient",
        this::mapRowToIngredient);
  }

  public Ingredient findOne(String id) {
    return jdbcTemplate.queryForObject(
        "select id, name, type from Ingredient where id=?",
        this::mapRowToIngredient, id);  }

  public Ingredient save(Ingredient ingredient) {
    jdbcTemplate.update(
        "insert into Ingredient (id, name, type) values (?, ?, ?)",
        ingredient.getId(),
        ingredient.getName(),
        ingredient.getType().toString());
    return ingredient;
  }

  private Ingredient mapRowToIngredient(ResultSet resultSet, int i) throws SQLException {
    return new Ingredient(
        resultSet.getString("id"),
        resultSet.getString("name"),
        Ingredient.Type.valueOf(resultSet.getString("type"))
    );
  }
}
