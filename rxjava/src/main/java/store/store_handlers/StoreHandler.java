package store.store_handlers;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import rx.Observable;
import store.mongo_shop.User;

import java.util.List;

public class StoreHandler {
    private StoreService service;

    public StoreHandler(StoreService service) {
        this.service = service;
    }

    public Observable<String> getResponse(HttpServerRequest<ByteBuf> request) {
        switch (request.getDecodedPath()) {
            case "/products":
                return service.getLocalProductsForUser(Long.parseLong(getQuery(request, "id")));
            case "/register":
                return service.addUser(Long.parseLong(getQuery(request, "id")), User.Currency.valueOf(getQuery(request, "currency")));
            case "/add-product":
                return service.addProduct(Long.parseLong(getQuery(request, "id")), Long.parseLong(getQuery(request, "price")), getQuery(request, "name"));
        }
        return Observable.just(request.getDecodedPath());
    }

    private String getQuery(HttpServerRequest<ByteBuf> request, String key) {
        List<String> query = request.getQueryParameters().get(key);
        return !query.isEmpty() ? query.get(0) : "";
    }
}
