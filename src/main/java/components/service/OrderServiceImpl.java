package components.service;

import components.entity.PaymentType;
import components.entity.Product;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class OrderServiceImpl implements OrderService {
    private HashMap<Integer, Integer> productsInBasket;
    private PaymentType paymentType;

    public OrderServiceImpl(){
        this.productsInBasket = new HashMap<>();
        this.paymentType = PaymentType.CASH;
    }

    public OrderServiceImpl(HashMap<Integer,Integer> productsInBasket, PaymentType paymentType) {
        this.productsInBasket = productsInBasket;
        this.paymentType = paymentType;
    }

    @Override
    public void addInBasket(Product product, int quantity){
        int id = product.getId();

        int currentQuantity = this.productsInBasket.getOrDefault(id,0);

        if(quantityIsEnough(currentQuantity + quantity,product.getQuantity()))
            this.productsInBasket.put(id,currentQuantity + quantity);
        else
            System.out.println("Not enough of this product is currently in the machine.");
    };

    @Override
    public void addInBasket(HashMap<Product,Integer> productsForBasket){
        for (Map.Entry<Product, Integer> entry : productsForBasket.entrySet()) {
            addInBasket(entry.getKey(),entry.getValue());
        }
    };

    @Override
    public void removeOneFromBasket(int id){
        if(this.productsInBasket.containsKey(id)){
            int resultingQuantity = this.productsInBasket.get(id)-1;

            if(resultingQuantity<=0){
                this.productsInBasket.remove(id);
            }
            else{
                this.productsInBasket.put(id,resultingQuantity);
            }
        }
        else{
            System.out.println("The basket doesn't contain any products with this id: " + id);
        }
    };

    @Override
    public void removeFromBasket(int id){
        if(this.productsInBasket.containsKey(id)){
            this.productsInBasket.remove(id);
        }
        else{
            System.out.println("The basket doesn't contain any products with this id: " + id);
        }
    }

    @Override
    public HashMap<Integer,Integer> getProductsInBasket() {
        return productsInBasket;
    }

    private PaymentType getPaymentType() {
        return this.paymentType;
    }

    private void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public void selectPaymentType(String type){
        type = type.toLowerCase();
        switch (type){
            case "cash":
                setPaymentType(PaymentType.CASH);
                break;
            case "debit card":
                setPaymentType(PaymentType.DEBIT_CARD);
                break;
            default:
                System.out.println("Invalid payment type: " + type);
                System.out.println("Current payment type is " + getPaymentType());
                break;
        }
    }

    @Override
    public void cancelOrder() {
        this.productsInBasket.clear();
        System.out.println("Order successfully canceled.");
    }

    private boolean quantityIsEnough(int quantityDemanded, int quantitySupplied){
        return quantitySupplied - quantityDemanded >= 0;
    }
}
