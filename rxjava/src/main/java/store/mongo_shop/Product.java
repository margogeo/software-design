package store.mongo_shop;


import org.bson.Document;

public class Product {

    private String name;
    private double rubPrice;

    public Product(Document document) {
        this.name = document.getString("name");
        this.rubPrice = document.getDouble("rubPrice");
    }

    public Product(String name, long price, User.Currency currency) {
        this.name = name;
        this.rubPrice = price * currency.toRub;
    }

    public Document toDocument() {
        return new Document().append("name", name).append("rubPrice", rubPrice);
    }

    public String toString(User.Currency currency) {
        return rubPrice / currency.toRub + currency.toString();
    }
}
