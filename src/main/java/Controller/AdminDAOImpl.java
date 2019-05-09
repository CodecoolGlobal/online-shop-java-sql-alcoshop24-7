package Controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Model.Product;
import View.DBTablePrinter;
import Model.Order;

public class AdminDAOImpl implements AdminDAO {
    private DBTablePrinter tablePrinter;
    private List<Product> allProducts;
    private List<Order> allOrders;
    private Connection connection = null;
    private DatabaseConnection dbconnection;
    private ResultSet rs = null;

    public void printAllProducts() throws SQLException {
        try {
            DatabaseConnection dbconnection = new DatabaseConnection();
            Connection connection = dbconnection.setConnection();
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(
                    "SELECT Products.ID, Products.Name, Types.Name, Products.Price, Products.Amount, Products.Available, Products.Rate"
                            + " FROM Products LEFT JOIN Types ON Products.TypeID = Types.ID;");
            rs = resultSet;
            tablePrinter = new DBTablePrinter();
            tablePrinter.printResultSet(rs);
            connection.commit();
            connection.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public List<Product> getListOfProducts() throws SQLException {
        allProducts = new ArrayList<>();
        try {
            DatabaseConnection dbconnection = new DatabaseConnection();
            Connection connection = dbconnection.setConnection();
            Statement stmt = connection.createStatement();

            ResultSet resultSet = stmt.executeQuery("SELECT Products.ID, Products.Name, Types.Name, Products.Price, Products.Amount, Products.Available, Products.Rate"
                    +" FROM Products LEFT JOIN Types ON Products.TypeID = Types.ID;");

            while (resultSet.next()){
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("Products.Name");
                String type = resultSet.getString("Types.Name");
                float price = resultSet.getFloat("Price");
                int amount = resultSet.getInt("Amount");
                String available = resultSet.getString("Available");
                int rate = resultSet.getInt("Rate");

                Product product = new Product(id, name, type, price, amount, available, rate);
                allProducts.add(product);
            }
            resultSet.close();
            stmt.close();
            connection.commit();
            connection.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return allProducts;
    }

    @Override
    public void addNewCategory(String categoryName){
        try {
            DatabaseConnection dbconnection = new DatabaseConnection();
            Connection connection = dbconnection.setConnection();
            Statement stmt = connection.createStatement();
            String sqlQuery ="INSERT INTO Types (Name) VALUES "+"('"+categoryName+"');";
            stmt.executeUpdate(sqlQuery);
            stmt.close();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCategoryName(int id, String newName){
        try {
            DatabaseConnection dbconnection = new DatabaseConnection();
            Connection connection = dbconnection.setConnection();
            Statement stmt = connection.createStatement();
            String sqlQuery ="UPDATE Types SET Name = '"+newName+"' WHERE ID ='"+id+"';";
            stmt.executeUpdate(sqlQuery);
            stmt.close();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void activateProduct(int ID){
        try {
            DatabaseConnection dbconnection = new DatabaseConnection();
            Connection connection = dbconnection.setConnection();
            Statement stmt = connection.createStatement();
            String sqlQuery ="UPDATE Products SET Available= 'true' WHERE ID="+ID+";";
            stmt.executeUpdate(sqlQuery);
            stmt.close();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deactivateProduct(int ID){
        try {
            DatabaseConnection dbconnection = new DatabaseConnection();
            Connection connection = dbconnection.setConnection();
            Statement stmt = connection.createStatement();
            String sqlQuery ="UPDATE Products SET Available= 'false' WHERE ID="+ID+";";
            stmt.executeUpdate(sqlQuery);
            stmt.close();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewProduct(String name, int typeID, float price, int amount, String available){
        try {
            DatabaseConnection dbconnection = new DatabaseConnection();
            Connection connection = dbconnection.setConnection();
            Statement stmt = connection.createStatement();
            String sqlQuery ="INSERT INTO Products (Name, TypeID, Price, Amount, Available) VALUES ('"+name+"',"+typeID+","+price+","+amount+","+available+");";
            stmt.executeUpdate(sqlQuery);
            stmt.close();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteProduct(int ID) {
        try {
            DatabaseConnection dbconnection = new DatabaseConnection();
            Connection connection = dbconnection.setConnection();
            Statement stmt = connection.createStatement();
            String sqlQuery ="DELETE FROM Products WHERE ID="+ID+";";
            stmt.executeUpdate(sqlQuery);
            stmt.close();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editProduct(int id, String newName, float price, int amount){
        try {
            DatabaseConnection dbconnection = new DatabaseConnection();
            Connection connection = dbconnection.setConnection();
            Statement stmt = connection.createStatement();
            String sqlQuery ="UPDATE Products SET Name = '"+newName+"', Price = "+price+", Amount = "+amount+" WHERE ID="+id+";";
            stmt.executeUpdate(sqlQuery);
            stmt.close();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void makeDiscount(int id, float discountPercent){
        float discountMultiplier = discountPercent/100;
        try {
            DatabaseConnection dbconnection = new DatabaseConnection();
            Connection connection = dbconnection.setConnection();
            Statement stmt = connection.createStatement();
            String sqlQuery ="UPDATE Products SET Price = Price-(Price*"+discountMultiplier+")"+" WHERE ID="+id+";";
            stmt.executeUpdate(sqlQuery);
            stmt.close();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> getAllOrders(){
        allOrders = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            DatabaseConnection dbconnection = new DatabaseConnection();
            Connection connection = dbconnection.setConnection();
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM Orders;");
            while (resultSet.next()){
                int id = resultSet.getInt("ID");
                int customerID = resultSet.getInt("CustomerID");
                int basketID = resultSet.getInt("BasketID");
                String status = resultSet.getString("Status");
                LocalDateTime creationDate = LocalDateTime.parse(resultSet.getString("CreationDate"), formatter);
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(creationDate);
                Order order = new Order(id, customerID, basketID, status, creationDate);
                allOrders.add(order);
            }
            resultSet.close();
            stmt.close();
            connection.commit();
            connection.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return allOrders;
    }

    @Override
    public void printAllCategories() throws SQLException {
        try {
            DatabaseConnection dbconnection = new DatabaseConnection();
            Connection connection = dbconnection.setConnection();
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(
                    "SELECT Types.ID, Types.Name FROM Types;");
            rs = resultSet;
            tablePrinter = new DBTablePrinter();
            tablePrinter.printResultSet(rs);
            connection.commit();
            connection.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void printAllOrders() throws SQLException {
        try {
            DatabaseConnection dbconnection = new DatabaseConnection();
            Connection connection = dbconnection.setConnection();
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(
                    "SELECT Orders.ID, Users.Login, Orders.Status, Orders.CreationDate"
                            +" FROM Orders LEFT JOIN Users ON Orders.CustomerID = Users.ID;");
            rs = resultSet;
            tablePrinter = new DBTablePrinter();
            tablePrinter.printResultSet(rs);
            connection.commit();
            connection.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

}


