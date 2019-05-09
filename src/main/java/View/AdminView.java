package View;

import Model.Customer;
import Model.Product;
import Model.Order;
import Model.User;

import java.util.List;
import java.util.Scanner;

public class AdminView {
    private Scanner scanner = new Scanner(System.in);

    public void printAdminMenu(){
        String adminMenu = "";
        adminMenu += "\n [1] Show all products";
        adminMenu += "\n [2] Add new category";
        adminMenu += "\n [3] Update category name";
        adminMenu += "\n [4] Deactivate product";
        adminMenu += "\n [5] Activate product";
        adminMenu += "\n [6] Create new product";
        adminMenu += "\n [7] Delete product";
        adminMenu += "\n [8] Edit product";
        adminMenu += "\n [9] Make a discount";
        adminMenu += "\n [10] See previous orders";
        adminMenu += "\n [11] Exit";
        System.out.println(adminMenu);
    }

    public int getIntegerInput() {
        while (!scanner.hasNextInt()) {
            System.out.println(("Enter an integer!"));
            scanner.nextLine();
        }
        int number = scanner.nextInt();
        scanner.nextLine();
        return number;
    }

    public boolean getBooleanInput() {
        while (!scanner.hasNextBoolean()) {
            System.out.println(("Enter an boolean!"));
            scanner.nextLine();
        }
        boolean bool = scanner.nextBoolean();
        scanner.nextLine();
        return bool;
    }


    public float getFloatInput() {
        while (!scanner.hasNextFloat()) {
            System.out.println(("Enter an float!"));
            scanner.nextLine();
        }
        float number = scanner.nextFloat();
        scanner.nextLine();
        return number;
    }

    public String getStringInput() {
        while (!scanner.hasNext()) {
            System.out.println(("Enter an string!"));
            scanner.nextLine();
        }
        String str = scanner.next();
        scanner.nextLine();
        return str;
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public String getStringAnswer(String message){
        System.out.println(message);
        Scanner sc = new Scanner(System.in);
        String answer = sc.nextLine();
        return answer;
    }

    public int getIntAnswer(String message){
        System.out.println(message);
        Scanner sc = new Scanner(System.in);
        int answer = sc.nextInt();
        return answer;
    }

    public void println(String message){
        System.out.println(message);
    }

    public void printProductList(List<Product> products){
        for (Product product: products) {
            System.out.println(product);

        }
    }

    public void printCustomerList(List<Customer> customers){
        for (User customer : customers) {
            System.out.println(customer);
        }
    }

    public void makeAPause(Integer miliseconds){
        try{
            Thread.sleep(miliseconds);
        }
        catch(InterruptedException ex){
            Thread.currentThread().interrupt();
        }
    }
}
