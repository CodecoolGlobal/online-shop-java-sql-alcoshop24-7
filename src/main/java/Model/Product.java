package Model;

import java.sql.Date;


public class Product {
    private int id;
    private String name;
    private int typeId;
    private float price;
    private int amount;
    private boolean isAvailable;
    int rate;

    
    public Product(int id, String name, int typeId, float price,
        int amount, String available, int rate){
        
        this.id = id;
        this.name = name;
        this.typeId = typeId;
        this.price = price;
        this.amount = amount;
        this.rate = rate;
        isAvailable = Boolean.valueOf(available);


    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name;}

    public int getTypeId(){
        return typeId;
    }

    public void setTypeId(int typeId){
        this.typeId= typeId;
    }

    public float getPrice(){
        return price;
    }

    public void setPrice(float price){
        this.price = price;
    }

    public int getAmount(){
        return amount; 
    }

    public void setAmount(int amount){
        this.amount = amount;
    }



    public String toString(){

        String result = String.format("Product ID: %d, name: %s, price: %.2f zł",
                getId(), getName(), getPrice());
        return result;
    }
}