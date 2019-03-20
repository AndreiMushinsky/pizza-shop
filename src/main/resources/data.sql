delete from Pizza_Order_Pizzas;
delete from Pizza_Ingredients;
delete from Pizza;
delete from Pizza_Order;

delete from Ingredient;
insert into Ingredient (id, name, type)
  values ('BCN', 'Bacon', 'MEAT');
insert into Ingredient (id, name, type)
  values ('SQD', 'Squid', 'SEA_PRODUCT');
insert into Ingredient (id, name, type)
  values ('TMT', 'Tomatoes', 'VEGETABLE');
insert into Ingredient (id, name, type)
  values ('LTC', 'Lettuce', 'VEGETABLE');
insert into Ingredient (id, name, type)
  values ('CHD', 'Cheddar', 'CHEESE');
insert into Ingredient (id, name, type)
  values ('JCK', 'Monterrey Jack', 'CHEESE');
insert into Ingredient (id, name, type)
  values ('SLS', 'Salsa', 'SAUCE');
insert into Ingredient (id, name, type)
  values ('SCR', 'Sour Cream', 'SAUCE');