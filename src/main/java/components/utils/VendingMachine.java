package components.utils;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import components.service.CoinService;
import components.service.OrderService;
import components.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

@Component
public class VendingMachine {
    @Parameter(names = "-option", description = "Gets the desired option for the program", required = false)
    private String option = "";

    private final String regularExpression = "[\\s]{0,},[\\s]{0,}";;

    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CoinService coinService;

    public void start(String[] args){
        Scanner s = new Scanner(System.in);

        try {
            populateData();
        }catch (Exception e){
            System.out.println("Something went wrong. Try again.");
        }

        Instructor.displayMenu();

        JCommander.newBuilder()
                .addObject(this)
                .build()
                .parse(args);

        if(option.equals(""))
            option = s.nextLine();

        while(!option.toUpperCase().equals("EXIT")) {
            String[] optionArguments = option.trim().split(regularExpression);
            commandRunner(optionArguments);
            option = s.nextLine();
        }
    };

    private void commandRunner(String[] optionArguments){
        try{
        switch (optionArguments[0]) {
            case "1":
                if(
                        optionArguments[1].equals("") ||
                        Double.parseDouble(optionArguments[2]) <= 0.0 ||
                        Integer.parseInt(optionArguments[3]) <= 0
                )
                    throw new Exception("Input valid parameters!");

                this.productService.addProduct(
                        optionArguments[1],
                        Double.parseDouble(optionArguments[2]),
                        Integer.parseInt(optionArguments[3])
                );
                break;

            case "2":
                this.productService.removeProduct(Integer.parseInt(optionArguments[1]));
                break;

            case "3":
                this.orderService.selectPaymentType(optionArguments[1]);
                break;

            case "4":
                String[] coinArray = Arrays.copyOfRange(optionArguments, 1, optionArguments.length);
                if(coinArray.length>=1)
                    this.coinService.userInsertCoins(false, coinArray);
                else
                    throw new Exception();
                break;

            case "5":
                this.productService.showProducts();
                break;

            case "6":
                if(this.orderService.getProductsInBasket().size() > 0)
                    this.productService.showBasket(this.orderService.getProductsInBasket());
                else{
                    System.out.println("Basket is empty.");
                }
                break;

            case "7":
                System.out.println(String.format("Your current input is: %1$,.2f euro.", this.coinService.getCurrentResources()));
                break;

            case "8":
                System.out.println(String.format("Your current bill is: %1$,.2f euro.", this.productService.getTotalPriceOfManyProducts(this.orderService.getProductsInBasket())));
                break;

            case "9":
                try{
                    int quantity = Integer.parseInt(optionArguments[2]);
                    if(quantity > 0)
                        this.orderService.addInBasket(
                                this.productService.getProduct(Integer.parseInt(optionArguments[1])),
                                quantity
                        );
                    else
                        throw new IllegalArgumentException();
                }
                catch (Exception e){
                    System.out.println("Operation could not be finished. Try with proper id and quantity.");
                    throw new Exception();
                }
                break;

            case "10":
                this.orderService.removeOneFromBasket(Integer.parseInt(optionArguments[1]));
                break;

            case "11":
                this.orderService.removeFromBasket(Integer.parseInt(optionArguments[1]));
                break;

            case "12":
                BigDecimal totalBill = this.productService.getTotalPriceOfManyProducts(this.orderService.getProductsInBasket());
                BigDecimal totalBudget = this.coinService.getCurrentResources();

                if(totalBudget.subtract(totalBill).compareTo(BigDecimal.ZERO) < 0){
                    System.out.println("Not enough money inserted. Please, insert more coins to continue your purchase.");
                }
                else if(totalBill.equals(BigDecimal.ZERO)){
                    System.out.println("Before making a purchase, please choose what you want to buy.");
                }
                else
                {
                    this.productService.updateProductQuantities(this.orderService.getProductsInBasket());

                    this.coinService.giveChange(totalBudget, totalBill);

                    this.orderService.getProductsInBasket().clear();
                }
                break;
            case "13":
                // cancel order
                this.orderService.cancelOrder();
                this.coinService.getCurrentCoins().clear();
                break;
            case "menu":
                Instructor.displayMenu();
                break;
            default:
                System.out.println("No such option. Type 'menu' to get available options.");
                break;
        }}
        catch (Exception e){
            System.out.println("Insert proper command.");
            Instructor.showInstruction(Integer.parseInt(optionArguments[0])-1);
        }
    }

    private void populateData() throws IOException {
        InputStreamReader reader = new InputStreamReader(
                getClass()
                        .getResourceAsStream("/preinsert-machine.txt"));

        BufferedReader br = new BufferedReader(reader);
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                String[] optionArguments = sCurrentLine.trim().split(regularExpression);

                if (optionArguments[0].equals("4")) {
                    this.coinService.userInsertCoins(true, Arrays.copyOfRange(optionArguments, 1, optionArguments.length));
                }
                else {
                    commandRunner(optionArguments);
                }
            }
        }
}
