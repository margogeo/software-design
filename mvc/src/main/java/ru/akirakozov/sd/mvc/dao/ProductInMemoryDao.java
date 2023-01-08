package ru.akirakozov.sd.mvc.dao;

import ru.akirakozov.sd.mvc.model.Point;
import ru.akirakozov.sd.mvc.model.Product;

import java.util.*;

/**
 * @author akirakozov
 */
public class ProductInMemoryDao implements ProductDao {
    private final HashMap<String, Product> products = new HashMap<>();

    public void addProduct(Product product) {
      String productName = product.getName();
      if (productName != "") {
        products.put(productName, product);
      }
    }

  public void deleteProduct(Product product) {
    products.remove(product.getName());
  }

  public Product getProduct(Product product) {
      return products.get(product.getName());
  }

  public void deletePoint(Product product, Point point) {
      product.deletePoint(point.getName());
  }

  public void addPoint(Product product, Point point) {
      product.addPoint(point);
  }

  public Point getPoint(Product product, Point point) {
      return product.getPoint(point.getName());
  }

  public boolean containsProduct(Product product) {
    if (products.get(product.getName()) != null)
      return true;
    return false;
  }

    public List<Product> getProducts() {
        return new ArrayList(products.keySet());
    }
}
