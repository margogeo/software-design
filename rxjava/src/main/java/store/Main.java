package store;

import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoDatabase;
import io.reactivex.netty.protocol.http.server.HttpServer;
import store.store_handlers.StoreHandler;
import store.store_handlers.StoreService;

public class Main {

    public static void main(final String[] args) {
        MongoDatabase db = MongoClients.create("mongodb://localhost:27017").getDatabase("shop");
        StoreService store = new StoreService(db.getCollection("users"), db.getCollection("products"));
        StoreHandler storeHandler = new StoreHandler(store);

        HttpServer.newServer(8080).start((req, resp) -> resp.writeString(storeHandler.getResponse(req))).awaitShutdown();
    }
}
