package ru.akirakozov.sd.mvc.dao;

import ru.akirakozov.sd.mvc.model.Point;
import ru.akirakozov.sd.mvc.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * @author akirakozov
 */
public interface ProductDao {
    void addProduct(Product product);
    void deleteProduct(Product product);
    Product getProduct(Product product);

    void addPoint(Product product, Point point);
    void deletePoint(Product product, Point point);
    Point getPoint(Product product, Point point);

    List<Product> getProducts();
    boolean containsProduct(Product product);
}
