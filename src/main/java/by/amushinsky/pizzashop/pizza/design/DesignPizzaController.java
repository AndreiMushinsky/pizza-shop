package by.amushinsky.pizzashop.pizza.design;

import by.amushinsky.pizzashop.pizza.order.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignPizzaController {

  private final IngredientRepository ingredientRepository;

  private final PizzaRepository pizzaRepository;

  @Autowired
  public DesignPizzaController(IngredientRepository ingredientRepository,
                               PizzaRepository pizzaRepository) {
    this.ingredientRepository = ingredientRepository;
    this.pizzaRepository = pizzaRepository;
  }

  @ModelAttribute("order")
  public Order order() {
    return new Order();
  }

  @ModelAttribute("pizza")
  public Pizza pizza() {
    return new Pizza();
  }

  @GetMapping
  public String showDesignForm(Model model) {
    final List<Ingredient> ingredients =
        StreamSupport.stream(ingredientRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());

    for (Ingredient.Type type : Ingredient.Type.values()) {
      model.addAttribute(type.toString().toLowerCase(), ingredients.stream()
          .filter(ingredient -> ingredient.getType() == type)
          .collect(Collectors.toList()));
    }
    return "design";
  }

  @PostMapping
  public String processDesign(@Valid Pizza pizza, Errors errors, @ModelAttribute Order order) {
    if (errors.hasErrors()) {
      return "design";
    }
    final Pizza saved = pizzaRepository.save(pizza);
    order.addPizza(saved);
    return "redirect:/orders/current";
  }

}
