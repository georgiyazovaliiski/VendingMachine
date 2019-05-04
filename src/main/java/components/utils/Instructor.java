package components.utils;

public class Instructor {
    private static String[] instructions = {
            "Input the following pattern: {1}, {item name}, {item price}, {item quantity}",
            "Input the following pattern: {2}, {item id}",
            "Input the following pattern: {3}, {type of payment}",
            "Input the following pattern: {4}, {coin 1}, {coin 2}, {coin 3}, ... , {coin N}\nThe machine works with \"one euro\", \"fifty cents\", \"twenty cents\", \"ten cents\", \"five cents\", \"two cents\", \"one cent\" ",
            "Input the following pattern: {5}",
            "Input the following pattern: {6}",
            "Input the following pattern: {7}",
            "Input the following pattern: {8}",
            "Input the following pattern: {9}, {product id}, {product quantity}",
            "Input the following pattern: {10}, {product id}",
            "Input the following pattern: {11}, {product id}",
            "Input the following pattern: {12}",
            "Input the following pattern: {13}"
    };

    public static void showInstruction(int index){
        System.out.println("Instructions for the previous command: ");
        System.out.println(instructions[index]);
    }



    public static void displayMenu(){
        System.out.println(String.format("\n%-50s %-50s %-50s", "[1]Add Product", "[2]Remove Product", "[3]Select Payment Type"));
        System.out.println(String.format("%-50s %-50s %-50s", "[4]Insert Coins", "[5]Show Products", "[6]Show Basket"));
        System.out.println(String.format("%-50s %-50s %-50s", "[7]Show Inserted Amount", "[8]Show Basket Amount", "[9]Add Product to Basket"));
        System.out.println(String.format("%-50s %-50s %-50s", "[10]Remove One Piece of Product", "[11]Remove All of a Product", "[12]Place Order"));
        System.out.println(String.format("%-50s %-50s", "[13]Cancel Order", "or type \'EXIT\'\n"));
    }
}
