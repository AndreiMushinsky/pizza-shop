package by.amushinsky.pizzashop.pizza.design;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignPizzaController {

  @GetMapping
  public String showDesignForm(Model model) {
    final List<Ingredient> ingredients = Arrays.asList(
        new Ingredient("BCN", "Bacon", Ingredient.Type.MEAT),
        new Ingredient("SQD", "Squid", Ingredient.Type.SEA_PRODUCT),
        new Ingredient("TMT", "Tomatoes", Ingredient.Type.VEGETABLE),
        new Ingredient("LTC", "Lettuce", Ingredient.Type.VEGETABLE),
        new Ingredient("CHD", "Cheddar", Ingredient.Type.CHEESE),
        new Ingredient("JCK", "Monterrey Jack", Ingredient.Type.CHEESE),
        new Ingredient("SLS", "Salsa", Ingredient.Type.SAUCE),
        new Ingredient("SCR", "Sour Cream", Ingredient.Type.SAUCE)
    );
    for (Ingredient.Type type : Ingredient.Type.values()) {
      model.addAttribute(type.toString().toLowerCase(), ingredients.stream()
          .filter(ingredient -> ingredient.getType() == type)
          .collect(Collectors.toList()));
    }
    model.addAttribute("design", new Pizza());
    return "design";
  }


  @PostMapping
  public String processDesign(Pizza pizza) {
    // TODO: save it to database
    log.info("Processing pizza: " + pizza);
    return "redirect:/orders/current";
  }

}
