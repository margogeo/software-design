package ru.akirakozov.sd.mvc.model;

public class Point {
  private int id;
  private String name;
  public boolean finished;

  public Point() {
  }

  public Point(int id, String name, Product list) {
    this.id = id;
    this.name = name;
    this.finished=false;
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

}
