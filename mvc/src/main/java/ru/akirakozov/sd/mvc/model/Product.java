package ru.akirakozov.sd.mvc.model;

import java.util.*;

public class Product {
    private int id;
    private String name;
    private HashMap<String, Point> list;

    public Product() {
      this.list = new HashMap<>();
    }

    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Point> getList() {
        return new ArrayList(list.values());
    }

    public void addPoint(Point point) {
        this.list.put(point.getName(), point);
    }

  public void deletePoint(String pointName) {
    this.list.remove(pointName);
  }

  public Point getPoint(String pointName) {
      return this.list.get(pointName);
  }

}
