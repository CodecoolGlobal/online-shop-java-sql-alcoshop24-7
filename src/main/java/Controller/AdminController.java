package Controller;

import java.sql.SQLException;
import java.util.*;
import Model.Product;
import Model.User;
import Model.Order;
import View.AdminView;

public class AdminController{
    public AdminDAOImpl adminDAO = new AdminDAOImpl();
    public AdminView adminView = new AdminView();

    public void updateCategoryName() throws SQLException{
        adminView.clearScreen();
        printAllCategories();
        adminView.println("Choose ID of category to update: ");
        int categoryID = adminView.getIntegerInput();
        adminView.clearScreen();
        adminView.println("Enter new category name: ");
        String newName = adminView.getStringInput();
        adminDAO.updateCategoryName(categoryID, newName);
        printAllCategories();
    }

    public void addNewCategory() throws  SQLException{
        adminView.clearScreen();
        adminView.println("New category name: ");
        String newCategoryName = adminView.getStringInput();
        adminDAO.addNewCategory(newCategoryName);
        printAllCategories();
    }

    public void addNewProduct() throws SQLException {
        adminView.clearScreen();
        printAllProducts();
        adminView.println("Enter product's name: ");
        String name = adminView.getStringInput();
        adminView.println("Enter category ID: ");
        int categoryID = adminView.getIntegerInput();
        adminView.println("Enter price: ");
        float price = adminView.getFloatInput();
        adminView.println("Enter new quantity: ");
        int quantity = adminView.getIntegerInput();
        adminView.println("Is the product available from beginning? (true/false)");
        String isAvailable = adminView.getStringInput();
        adminDAO.addNewProduct(name, categoryID, price, quantity, isAvailable);
        printAllProducts();
    }

    public void deleteProduct() throws SQLException{
        adminView.clearScreen();
        printAllProducts();
        adminView.println("Enter ID of product which you want to delete: ");
        int productID = adminView.getIntegerInput();
        adminDAO.deleteProduct(productID);
        printAllProducts();
    }

    public void editProduct() throws SQLException {
        adminView.clearScreen();
        printAllProducts();
        adminView.println("Enter ID of product which you want to edit: ");
        int productID = adminView.getIntegerInput();
        adminView.println("New name: ");
        String newName = adminView.getStringInput();
        adminView.println("Enter new price: ");
        float price = adminView.getFloatInput();
        adminView.println("Enter new quantity: ");
        int quantity = adminView.getIntegerInput();
        adminDAO.editProduct(productID, newName, price, quantity);
        printAllProducts();
    }

    public void makeDiscount() throws SQLException {
        adminView.clearScreen();
        printAllProducts();
        adminView.println("Enter ID of product to lower it's price: ");
        int productID = adminView.getIntegerInput();
        adminView.println("Enter percent number (1-100) of the price reduction: ");
        float discountPercent = adminView.getFloatInput();
        adminDAO.makeDiscount(productID, discountPercent);
        printAllProducts();
    }

    public void deactivateProduct() throws SQLException {
        adminView.clearScreen();
        printAllProducts();
        adminView.println("Enter ID of product which you want to deactivate:");
        int productID = adminView.getIntegerInput();
        adminDAO.deactivateProduct(productID);
        printAllProducts();
    }

    public void activateProduct() throws SQLException {
        adminView.clearScreen();
        printAllProducts();
        adminView.println("Enter ID of product which you want to activate: ");
        int productID = adminView.getIntegerInput();
        adminDAO.activateProduct(productID);
        printAllProducts();
    }

    public List<Product> getAllProducts() throws SQLException{
        return adminDAO.getListOfProducts();
    }

    public void printAllProducts() throws SQLException{
        adminView.clearScreen();
        adminDAO.printAllProducts();
    }

    public List<Order> getAllOrders() throws SQLException{
        return adminDAO.getAllOrders();
    }

    public void printAllOrders() throws SQLException{
        adminView.clearScreen();
        adminDAO.printAllOrders();
    }

    public void printAllCategories() throws  SQLException{
        adminView.clearScreen();
        adminDAO.printAllCategories();
    }
}
