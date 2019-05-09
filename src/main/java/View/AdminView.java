package View;

import Model.Customer;
import Model.Product;
import Model.Order;
import Model.User;

import java.util.List;
import java.util.Scanner;

public class AdminView {
    public void printAllProducts(List<Product> products){
        for (Product product: products
             ) {
            System.out.println("ID: " + product.getId() +", " + "name: " + product.getName()+", "  + "vol(%): " +", "  + "Amount: " +
                    product.getAmount()+", "  + ", "  + "price: " + product.getPrice());
        }
    }

    public void printAllCustomers(List<User> customers){
        for (User user: customers
             ) {
            System.out.println("ID: " + user.getId() + ", Login: " + user.getLogin() + ", Password: " + user.getPassword());
        }
    }

    public void printAllOrders(List<Order> orders){
        for (Order order: orders
             ) {
            System.out.println("ID: " + order.getID() + ", UserID: " + order.getCustomerID() + ", BasketID: " + order.getBasketID() + ", status: " + order.getStatus() + ", creation data" + order.getCreationDate()  );
        }
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
    public float getFloatAnswer(String message){
        System.out.println(message);
        Scanner sc = new Scanner(System.in);
        float answer = sc.nextFloat();
        return answer;
    }
}
