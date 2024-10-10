package service;

import entities.Category;
import entities.Product;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.*;
import java.time.LocalDate;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;


@ApplicationScoped
public class WarehouseService {
    private final List<Product> products = new CopyOnWriteArrayList<>();

    public void resetProducts() {
        products.clear();
    }


    public void main(String[] args) {

    }

    public void addProductForTest() {
        products.add(new Product(1, "Apelsin", Category.FRUITS, 5, LocalDate.of(2024, 12, 30), LocalDate.of(2024, 12, 30)));
        products.add(new Product(2, "Banan", Category.FRUITS, 1, LocalDate.of(2023, 8, 25), LocalDate.of(2024, 2, 23)));
        products.add(new Product(3, "Kyckling", Category.MEAT, 9, LocalDate.of(2024, 11, 22), LocalDate.of(2024, 3, 11)));
        products.add(new Product(5, "Pepsi", Category.DRINK, 6, LocalDate.of(2023, 6, 12), LocalDate.of(2024, 9, 2)));
        products.add(new Product(7, "Zola", Category.DRINK, 10, LocalDate.of(2023, 6, 12), LocalDate.of(2024, 6, 2)));
        products.add(new Product(6, "Fanta", Category.DRINK, 1, LocalDate.of(2023, 6, 12), LocalDate.of(2023, 8, 12)));
        products.add(new Product(8, "Fläsk", Category.MEAT, 8, LocalDate.of(2023, 4, 4), LocalDate.of(2024, 4, 4)));
    }

    public List<Product> getAllProducts() {


        return Collections.unmodifiableList(products);
    }

    ;


    public Optional<Product> getProductById(int id) {

        return products.stream()
                .filter(product -> product.id() == id)
                .findFirst();

    }

    public List<Product> getProductByCategorySortedByName(Category category) {
        return products.stream()
                .filter(product -> product.category() == category)
                .sorted(Comparator.comparing(Product::name))
                .collect(Collectors.toUnmodifiableList());

    }

    public void addNewProduct(Product product) {
        if (product.name().isEmpty()) {
            throw new IllegalArgumentException("Product name can't be empty");
        }
        products.add(product);

    }

    public void modifyProduct(int id, String newName, Category newCategory, int newRating) {
        Product product = products.stream()
                .filter(p -> p.id() == id).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Id is not available"));
        Product modifiedProduct = new Product(product.id(), newName, newCategory, newRating, product.createdAt(), LocalDate.now());

        products.set(products.indexOf(product), modifiedProduct);

    }

    public List<Product> getProductsAfterGivenDate(LocalDate dateAfter) {
        return products.stream().filter(product -> product.createdAt().isAfter(dateAfter)).collect(Collectors.toUnmodifiableList());

    }

    public List<Product> getProductsThatHasBeenModified() {
        return products.stream()
                .filter(product -> product.modifiedAt() != null && !product.createdAt()
                        .isEqual(product.modifiedAt()))
                .collect(Collectors.toUnmodifiableList());
    }


}
