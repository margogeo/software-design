package ru.akirakozov.sd.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.akirakozov.sd.mvc.dao.ProductDao;
import ru.akirakozov.sd.mvc.model.Point;
import ru.akirakozov.sd.mvc.model.Product;

import java.util.*;

/**
 * @author akirakozov
 */
@Controller
public class ProductController {
    private final ProductDao productDao;
    private Product currentProduct;
    private boolean init = true;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @RequestMapping(value = "/add-product", method = RequestMethod.POST)
    public String addProduct(@ModelAttribute("product") Product product) {
      if (!productDao.containsProduct(product))
        productDao.addProduct(product);
      return "redirect:/get-products";
    }

  @RequestMapping(value = "/delete-product", method = RequestMethod.POST)
  public String deleteProduct(@ModelAttribute("product") Product product) {
    productDao.deleteProduct(product);
    return "redirect:/get-products";
  }

    @RequestMapping(value = "/get-products", method = RequestMethod.GET)
    public String getProducts(ModelMap map) {
        prepareModelMap(map, productDao.getProducts());
        return "index";
    }

  @RequestMapping(value = "/open-product", method = RequestMethod.POST)
  public String openProduct(@ModelAttribute("product") Product product) {
      if (productDao.containsProduct(product)) {
        if (this.currentProduct != null) {
          productDao.addProduct(this.currentProduct);
        }
        this.currentProduct = productDao.getProduct(product);
      }
      return "redirect:/get-products";
  }

  @RequestMapping(value = "/add-point", method = RequestMethod.POST)
  public String addPoint(@ModelAttribute("point") Point point, ModelMap map) {
    productDao.addPoint(this.currentProduct, point);
    map.addAttribute("currentList", this.currentProduct);
    return "redirect:/get-products";
  }

  @RequestMapping(value = "/delete-point", method = RequestMethod.POST)
  public String deletePoint(@ModelAttribute("point") Point point, ModelMap map) {
    productDao.deletePoint(this.currentProduct, point);
    map.addAttribute("currentList", this.currentProduct);
    return "redirect:/get-products";
  }

  @RequestMapping(value = "/finish-point", method = RequestMethod.POST)
  public String finishPoint(@ModelAttribute("point") Point point, ModelMap map) {
    Point curPoint = productDao.getPoint(this.currentProduct, point);
    curPoint.finished = true;
    map.addAttribute("currentList", this.currentProduct);
    return "redirect:/get-products";
  }

    private void prepareModelMap(ModelMap map, List<Product> products) {
        map.addAttribute("products", products);
        map.addAttribute("currentList", this.currentProduct);
    }
}
