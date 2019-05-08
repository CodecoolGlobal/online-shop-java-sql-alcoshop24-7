package Model;

import java.time.LocalDateTime;
import java.util.Date;



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

    public String toString(){
        String string = new String("ID: " + getID() + ", UserID: " + getCustomerID() + ", BasketID: " + getBasketID() + ", status: " + getStatus() + ", creation data: " + getCreationDate()  );


        return string;
    }




}
