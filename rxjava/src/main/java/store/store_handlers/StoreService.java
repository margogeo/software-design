package store.store_handlers;

import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.MongoCollection;
import org.bson.Document;
import rx.Observable;
import store.mongo_shop.Product;
import store.mongo_shop.User;

public class StoreService {
    private MongoCollection<Document> users, products;
    public StoreService(MongoCollection<Document> users, MongoCollection<Document> products) {
        this.users = users;
        this.products = products;
    }

    public Observable<String> getLocalProductsForUser(long userId) {
        return Observable.combineLatest(this.getUser(userId).map(user -> user.getCurrency()), this.getProducts(), (cur, product) -> product.toString(cur));
    }

    public Observable<String> addUser(long userId, User.Currency currency) {
        return this.addUser(new User(userId, currency));
    }

    public Observable<String> addProduct(long userId, long price, String name) {
        return this.getUser(userId).map(user -> user.getCurrency()).switchMap(currency -> this.addProduct(new Product(name, price, currency)));
    }

    public Observable<String> addUser(User user) {
        return users.insertOne(user.toDocument()).map(usr -> usr == null ? "error" : "success");
    }

    public Observable<String> addProduct(Product product) {
        return products.insertOne(product.toDocument()).map(prod -> prod == null ? "error" : "success");
    }

    public Observable<User> getUser(long userId) {
        return users.find(Filters.eq("id", userId)).first().map(doc -> new User(doc));
    }

    public Observable<Product> getProducts() {
        return products.find().toObservable().map(doc -> new Product(doc));
    }
}
