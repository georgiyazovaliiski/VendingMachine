package components.service;

import components.entity.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private List<Product> products;
    private int lastProductId;

    public ProductServiceImpl() {
        this.products = new ArrayList<>();
        lastProductId = 0;
    }

    @Override
    public void addProduct(String name, double price, int quantity) {
        products.add(new Product(updateLastProductId(),name, price, quantity));
    }

    @Override
    public void removeProduct(int id) {
        int index = getIndexOfProduct(id);

        if(index>=0 && index<products.size()){
            products.remove(index);
        }
        else{
            System.out.printf("Product with id: %d could not be found.\n", id);
        }
    }

    @Override
    public void removeProduct(Product product) {
        if(products.contains(product))
            products.remove(product);
        else{
            System.out.printf("Product with id: %d could not be found.\n", product.getId());
        }
    }

    @Override
    public int getIndexOfProduct(int id) {
        return Collections.binarySearch(products, new Product(id), new Comparator<Product>() {
            public int compare(Product u1, Product u2) {
                return u1.getId().compareTo(u2.getId());
            }
        });
    }

    @Override
    public int updateLastProductId() {
        return ++lastProductId;
    }

    @Override
    public void showProducts() {
        System.out.println(
                String.format("%-14s| ", "-Id-") +
                        String.format("%-14s| ", "-Name-") +
                        String.format("%-14s", "-Price-")
        );
        for (Product product : products.stream().filter(p->p.getQuantity() > 0).collect(Collectors.toList())) {
            System.out.println(
                    String.format("%-14s| ", product.getId()) +
                            String.format("%-14s| ", product.getName()) +
                            String.format("%-14.2f ", product.getPrice())
            );
        }
    }

    @Override
    public Product getProduct(int id) {
        try{
            return this.products.get(getIndexOfProduct(id));
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public void updateProductQuantities(HashMap<Integer, Integer> productsInBasket) {
        for (Map.Entry<Integer, Integer> productEntry : productsInBasket.entrySet()) {
            int currentProductIndex = this.getIndexOfProduct(productEntry.getKey());
            int productQuantitySupplied = this.products.get(currentProductIndex).getQuantity();

            if(productQuantitySupplied - productEntry.getValue() > 0) {
                this.products
                        .get(currentProductIndex)
                        .setQuantity(productQuantitySupplied - productEntry.getValue());
            }
            else{
                this.products.remove(currentProductIndex);
            }
        }
    }

    @Override
    public BigDecimal getTotalPriceOfManyProducts(HashMap<Integer, Integer> productsInBasket) {
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (Map.Entry<Integer, Integer> entrySet : productsInBasket.entrySet()) {
            totalPrice = totalPrice.add(
                    BigDecimal.valueOf(getProduct(entrySet.getKey()).getPrice())
                            .multiply(BigDecimal.valueOf(entrySet.getValue())));
        }

        return totalPrice;
    }

    @Override
    public void showBasket(HashMap<Integer, Integer> productsInBasket) {
        System.out.println(
                String.format("%-14s| ", "-Id-") +
                        String.format("%-14s| ", "-Name-") +
                        String.format("%-20s| ", "-Single Price-") +
                        String.format("%-14s", "-Quantity-")
        );

        for (Map.Entry<Integer, Integer> entrySet : productsInBasket.entrySet()) {
            Product currentProduct = this.products.get(getIndexOfProduct(entrySet.getKey()));
            System.out.println(
                    String.format("%-14s| ", currentProduct.getId()) +
                            String.format("%-14s| ", currentProduct.getName()) +
                            String.format("%-20.2f| ", currentProduct.getPrice()) +
                            String.format("%-14s", entrySet.getValue())
            );
        }
    }
}
