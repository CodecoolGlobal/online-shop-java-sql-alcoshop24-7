package Model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



public class Order{

    private int ID;
    private int customerID;
    private int basketID;
    private String status;
    private LocalDateTime creationDate;
    private Basket basket;





    public Order(int ID, int customerID, int basketID, String status, LocalDateTime creationDate, Basket basket) {
        this.ID = ID;
        this.customerID = customerID;
        this.basketID = basketID;
        this.status = status;
        this.creationDate = creationDate;
        this.basket = basket;


    }
    public int getBasketID() {
        return basketID;
    }


    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public int getID() {
        return ID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getStatus() {
        return status;
    }

    public Basket getBasket() {
        return basket;
    }

    public String toString(){
        String string = ("ID: " + getID() + ", UserID: " + getCustomerID() + ", BasketID: " + getBasketID() + ", status: " + getStatus() + ", creation data: " + getCreationDate()  );
        StringBuilder products = new StringBuilder();
        //Basket basket = this.basket;
        Map<Product, Integer> basketContent = this.basket.getProducts();

        for (Map.Entry<Product, Integer> entry: basketContent.entrySet()) {
            String ammount = entry.getValue().toString();
            String productName = entry.getKey().getName();
            products.append(" Product: ");
            products.append(productName);
            products.append(", ammount: ");
            products.append(ammount + "\n");
        }
        String productsStr = products.toString();
        return string + productsStr;
    }




}
