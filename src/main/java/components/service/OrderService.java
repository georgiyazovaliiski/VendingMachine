package components.service;

import components.entity.Product;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public interface OrderService {
    void addInBasket(Product product, int quantity);
    void addInBasket(HashMap<Product,Integer> productsForBasket);
    void removeOneFromBasket(int id);
    void removeFromBasket(int id);
    void selectPaymentType(String paymentType);

    void cancelOrder();

    HashMap<Integer, Integer> getProductsInBasket();
}
