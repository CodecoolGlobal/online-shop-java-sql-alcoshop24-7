package Controller;

import java.sql.SQLException;
import java.util.*;

import Model.Order;
import Model.Product;
import View.CustomerView;

public class CustomerController{
    CustomerDAOImpl dao = new CustomerDAOImpl();
    CustomerView view = new CustomerView();

    //michals function
//    public void deactiveProduct(){
//        dao.deactivateProduct();
//    }

    public List<Product> getAllProducts() throws SQLException{
        return dao.getAllProducts();
    }



    public List<Order> getOrders(int customerID)throws SQLException{
        return dao.getOrders(customerID);
    }

    public Order getOrder(int orderID)throws SQLException{
        return dao.getOrder(orderID);
    }

    public Product getProductById(int productID)throws SQLException{
        return dao.getProductById(productID);
    }

    public void addOrder(Order order){
        dao.addNewOrder(order);
    }


}