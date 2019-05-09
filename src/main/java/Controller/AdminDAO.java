package Controller;


import java.sql.SQLException;
import java.util.List;
import Model.Category;

import Model.Product;
import Model.User;
import Model.Order;

public interface AdminDAO {

    void printAllProducts() throws SQLException;

    List<Product> getListOfProducts() throws SQLException;

    void addNewCategory(String categoryName);

    void updateCategoryName(int ID, String newName);

    void deactivateProduct(int ID);

    void activateProduct(int ID);

    void deleteProduct(int ID);

    void addNewProduct(String name, int categoryID, float price, int quantity, String available);

    void editProduct(int id, String newName, float price, int quantity);

    void makeDiscount(int id, float discountPercent);

    List<Order> getAllOrders() throws SQLException;

    void printAllOrders() throws SQLException;

    void printAllCategories() throws SQLException;

}