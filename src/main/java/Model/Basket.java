package Model;

import java.util.*;

public class Basket{
    private int ID;
    private Iterator<Product> iterator;
    private Map<Product, Integer> products;

    public Basket(int ID, Map<Product, Integer> products){
        this.ID = ID;
        this.products = products;
    }

    public Iterator<Product> getIterator(){
        return iterator;
    }

    public int getID(){
        return ID;
    }

    public Map getProducts(){
        return products;
    }

    public void addProduct(Product product, int ammount){
        products.put(product, ammount);
    }



    public String toString(){
        return "";
    }

}