package Controller;

import java.sql.*;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Model.*;

public class AdminDAOImpl implements AdminDAO {
    private List<Product> allProducts;
    private List<User> allCustomers;
    private List<Order> allOrders;
    private final DateFormat FORMAT = new SimpleDateFormat("yyyy-mm-dd");


    @Override
    public List<Product> getAllProducts() throws SQLException {
        allProducts = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:alcoshop.db");
            Statement stmt = connection.createStatement();
            connection.setAutoCommit(false);

            ResultSet resultSet = stmt.executeQuery("SELECT * FROM Products;");

            while (resultSet.next()){
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("Name");
                int typeID = resultSet.getInt("TypeID");
                float price = resultSet.getFloat("Price");
                float alcoholContent = resultSet.getFloat("Vol.(%)");
                float volume = resultSet.getFloat("Vol(l)");
                int amount = resultSet.getInt("Amount");
                java.util.Date expDate = FORMAT.parse(resultSet.getString("ExpDate"));
                java.sql.Date sqlExpDate = new java.sql.Date(expDate.getTime());
                Product product = new Product(id, name, typeID, price, alcoholContent,
                volume, amount, sqlExpDate);
                
                allProducts.add(product);
            }
            resultSet.close();
            stmt.close();
            connection.commit();
            connection.close();
        } catch (ParseException | ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        return allProducts;
    }

    @Override
    public List<User> getAllCustomers() throws SQLException {
        allCustomers = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:alcoshop.db");
            Statement stmt = connection.createStatement();
            connection.setAutoCommit(false);
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM Users;");
            while (resultSet.next()){
                int userId = resultSet.getInt("ID");
                int userTypeId = resultSet.getInt("UserTypeID");
                String login = resultSet.getString("Login");
                String pass = resultSet.getString("Password");
                if (userTypeId == 2) {
                    User user = new Customer(userId, userTypeId, login, pass);
                    allCustomers.add(user);
                }
            }


            resultSet.close();
            stmt.close();
            connection.commit();
            connection.close();
        } catch (ClassNotFoundException ex){
            System.err.println(ex.getMessage());
        }
        return allCustomers;
    }

    @Override
    public void refillTheStock(Product product, int amount) throws SQLException {
        try {Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:alcoshop.db");
            PreparedStatement stmt;
            connection.setAutoCommit(false);
            String sqlStatments = "update Products "
                    + " set Amount= ?"
                    + " where id= ?";

            stmt = connection.prepareStatement(sqlStatments);

            stmt.setInt(1, amount);
            stmt.setInt(2, product.getId());

            stmt.executeUpdate();




            stmt.close();
            connection.commit();
            connection.close();

        }catch (ClassNotFoundException ex){
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void addCategory(Category category) throws SQLException {
        try {Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:alcoshop.db");
            Statement stmt = connection.createStatement();
            connection.setAutoCommit(false);
            String sqlSentence = "";
            //TODO
        }catch (ClassNotFoundException ex){
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void createNewProduct(Product product) throws  SQLException {

        try {Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:alcoshop.db");
            PreparedStatement stmt;
            connection.setAutoCommit(false);
            String sqlStatments = "insert into Products"
                    + "(ID, Name, TypeID, Price, [Vol.(%)], [Vol(l)], Amount, ExpDate)"
                    + " values (?, ? ,? ,? ,? ,? , ?, ?)";

            stmt = connection.prepareStatement(sqlStatments);



            List<Product> products = new ArrayList<>(getAllProducts());
            stmt.setInt(1, products.size() + 1);
            stmt.setString(2, product.getName());
            stmt.setInt(3, product.getTypeId());
            stmt.setFloat(4, product.getPrice());
            stmt.setFloat(5, product.getAlcoholContent());
            stmt.setFloat(6, product.getVolume());
            stmt.setInt(7, product.getAmount());
            stmt.setString(8, product.getExpirationDate().toString());


            stmt.executeUpdate();




            stmt.close();
            connection.commit();
            connection.close();


        }catch (ClassNotFoundException ex){
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public List<Order> getOrders() throws  SQLException {
        allOrders = new ArrayList<>();
        try {Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:alcoshop.db");
            Statement stmt = connection.createStatement();
            connection.setAutoCommit(false);
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM Orders;");
            while (resultSet.next()){
                int id = resultSet.getInt("ID");
                int customerId = resultSet.getInt("CustomerID");
                int basketID = resultSet.getInt("BasketID");
                Order order = new Order(id, basketID, customerId);
                allOrders.add(order);

            }
            resultSet.close();
            stmt.close();
            connection.commit();
            connection.close();

        }catch (ClassNotFoundException ex){
            System.err.println(ex.getMessage());
        }
        return allOrders;
    }

    @Override
    public void deactivateProduct(Product product) throws  SQLException {
        try {Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:alcoshop.db");
            Statement stmt = connection.createStatement();
            connection.setAutoCommit(false);

        }catch (ClassNotFoundException ex){
            System.err.println(ex.getMessage());
        }

    }

    @Override
    public void deactivateProductIf0() throws  SQLException {
        try {Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:alcoshop.db");
            Statement stmt = connection.createStatement();
            connection.setAutoCommit(false);

        }catch (ClassNotFoundException ex){
            System.err.println(ex.getMessage());
        }

    }
}