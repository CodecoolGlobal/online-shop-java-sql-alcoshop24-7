package Controller;
import Model.Basket;
import Model.Product;
import Model.Order;
import Model.WineType;
import Model.User;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO{
    public List<Product> getProducts() throws SQLException;
    public Product getProductById(int productId) throws SQLException;
    public List<Order> getOrders() throws SQLException;
    public Order getOrder(int orderId) throws SQLException;
    public Basket getBasket(int orderId) throws SQLException;
    public boolean checkIsAvailable() throws SQLException;

}