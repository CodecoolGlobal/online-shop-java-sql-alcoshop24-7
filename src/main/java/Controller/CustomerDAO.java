package Controller;
import Model.Basket;
import Model.Product;
import Model.Order;
import Model.WineType;
import Model.User;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO{
    List<Product> getAllProducts() throws SQLException;
    Product getProductById(int productId) throws SQLException;
    List<Order> getOrders(int customerId) throws SQLException;
    Order getOrder(int orderId) throws SQLException;



    void addNewOrder(Order order);


}