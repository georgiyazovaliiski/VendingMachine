package components.service;


import components.entity.Product;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;

public interface ProductService {
    void addProduct(String name, double price, int quantity);
    void removeProduct(int id);
    void removeProduct(Product product);
    int getIndexOfProduct(int id);
    int updateLastProductId();
    void showProducts();
    Product getProduct(int id);
    void updateProductQuantities(HashMap<Integer, Integer> productsInBasket);

    BigDecimal getTotalPriceOfManyProducts(HashMap<Integer,Integer> productsInBasket);
    void showBasket(HashMap<Integer,Integer> productsInBasket);
}
