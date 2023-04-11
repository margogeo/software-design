package store.mongo_shop;

import org.bson.Document;

public class User {
    private long id;
    private Currency currency;

    public User(Document document) {
        this.id = document.getLong("id");
        this.currency = Currency.valueOf(document.getString("currency"));
    }

    public User(long id, Currency currency) {
        this.id = id;
        this.currency = currency;
    }

    public Document toDocument() {
        return new Document().append("id", id).append("currency", currency.toString());
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", currency='" + currency + '\'' + '}';
    }

    public enum Currency {
        USD(81), EUR(88), RUB(1);

        public double toRub;

        Currency(double toRub) {
            this.toRub = toRub;
        }
    }
}
