package database;

import models.Category;
import models.Product;
import models.ProductType;

import java.util.ArrayList;
public class Database {
    public static ArrayList<Product> getProducts(){
        ArrayList<Product> products = new ArrayList<>();
        Product a = new Product("1", ProductType.ALCOHOLIC, "vodka", "Флирт", 2.20,1);
        Product b = new Product("2", ProductType.NONALCOHOLIC, "soda", "Кока кола", 2.00, 1);
        Product c = new Product("3", ProductType.FOOD,"nuts", "Бадеми", 5.00, 1);
        Product a1 = new Product("4", ProductType.ALCOHOLIC, "vodka", "Смирноф", 2.20, 1);
        Product b1 = new Product("5", ProductType.NONALCOHOLIC,"soda", "Пепси", 2.00, 1);
        Product c1 = new Product("6", ProductType.FOOD,"nuts", "Фъстъци", 5.00, 1);
        Product a2 = new Product("7", ProductType.ALCOHOLIC,"vodka", "Руски стандарт", 2.20, 1);
        Product b2 = new Product("8", ProductType.NONALCOHOLIC,"soda", "Фанта", 2.00, 1);
        Product c2 = new Product("9", ProductType.FOOD, "nuts", "Лешници", 5.00, 1);

        products.add(a);
        products.add(b);
        products.add(c);
        products.add(a1);
        products.add(b1);
        products.add(c1);
        products.add(a2);
        products.add(b2);
        products.add(c2);

        return products;

    }
    public static ArrayList<Category> getCategories(){
        ArrayList<Category> categories = new ArrayList<>();
        Category alcoholic = new Category("Алкохолни", ProductType.ALCOHOLIC);
        Category nonalcoholic = new Category("Безалкохолни", ProductType.NONALCOHOLIC);
        Category food = new Category("Храна", ProductType.FOOD);
        categories.add(alcoholic);
        categories.add(nonalcoholic);
        categories.add(food);
        return categories;
    }
}
