package Controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import Controller.CustomerDAO;
import Model.Product;
import Model.Basket;
import Model.Order;
import sun.misc.Resource;

import java.sql.*;
import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;


public class CustomerDAOImpl implements CustomerDAO {
    private final DateFormat FORMAT = new SimpleDateFormat("yyyy-mm-dd");
    private List<Product> allProducts;
    private List<Order> myOrders;


    public List<Product> getAllProducts() throws SQLException {
        allProducts = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:alcoshop.db");
            Statement stmt = connection.createStatement();
            connection.setAutoCommit(false);

            ResultSet resultSet = stmt.executeQuery("SELECT * FROM Products;");

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("Name");
                int typeID = resultSet.getInt("TypeID");
                float price = resultSet.getFloat("Price");
                //float alcoholContent = resultSet.getFloat("Vol.(%)");
                //float volume = resultSet.getFloat("Vol(l)");
                int amount = resultSet.getInt("Amount");
                java.util.Date expDate = FORMAT.parse(resultSet.getString("ExpDate"));
               // java.sql.Date sqlExpDate = new java.sql.Date(expDate.getTime());
                Product product = new Product(id, name, typeID, price, amount,);

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

    public Basket getBasket(int orderID) {
        return null;
    }


    private Connection setConnection() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:alcoshop.db");
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return connection;
    }


    public List<Order> getOrders(int customerId) throws SQLException {
        myOrders = new ArrayList<>();
        Connection con = setConnection();
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            con.setAutoCommit(false);
            stmt = con.prepareStatement("SELECT * FROM Orders WHERE CustomerID=?");
            stmt.setInt(1, customerId);

            resultSet = stmt.executeQuery();

            while(resultSet.next()) {
                int orderID = resultSet.getInt("ID");
                int basketID = resultSet.getInt("BasketID");
                int userID = resultSet.getInt("CustomerID");
                myOrders.add(new Order(orderID, basketID, userID));}

            con.commit();
            con.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return myOrders;
    }

    public Order getOrder(int orderId) throws SQLException {
        Connection conn = setConnection();
        PreparedStatement statm = null;
        ResultSet resSet = null;
        Order order = null;

        try {
            statm = conn.prepareStatement("SELECT * FROM Orders WHERE orderId = ?");
            conn.setAutoCommit(false);
            statm.setInt(1, orderId);
            resSet = statm.executeQuery();

            int orderID = resSet.getInt("ID");
            int basketID = resSet.getInt("BasketID");
            int userID = resSet.getInt("CustomerID");
            order = new Order(orderID, basketID, userID);

            conn.commit();
            conn.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return order;
    }

    public Product getProductById(int productID) {
        Product product = null;
        Connection connection = setConnection();
        ResultSet resultSet2 = null;

        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("SELECT name FROM Products WHERE id=?");
            statement.setInt(1, productID);
            resultSet2 = statement.executeQuery();

            int id = resultSet2.getInt("ID");
            String name = resultSet2.getString("Name");
            int typeID = resultSet2.getInt("TypeID");
            float price = resultSet2.getFloat("Price");
            float alcoholContent = resultSet2.getFloat("Vol.(%)");
            float volume = resultSet2.getFloat("Vol(l)");
            int amount = resultSet2.getInt("Amount");
            java.util.Date expDate = FORMAT.parse(resultSet2.getString("ExpDate"));
            java.sql.Date sqlExpDate = new java.sql.Date(expDate.getTime());
            product = new Product(id, name, typeID, price, alcoholContent,
                    volume, amount, sqlExpDate);

            resultSet2.close();
            statement.close();
            connection.close();

        } catch (Exception exception) {
            System.err.println(exception);

        }
        return product;

    }

    public void makeNewOrder(int ID, int basketID, int userID) {
        Statement newOrStat = null;
        Connection newOrCon = setConnection();

        try {
            newOrCon.setAutoCommit(false);
            newOrStat = newOrCon.createStatement();
            String sql = "INSERT INTO Orders " +
                    "VALUES (ID, basketID, userID),";
            newOrStat.executeUpdate(sql);
        } catch (Exception exception) {
            System.err.println(exception);
        }
    }

//    public boolean isAvailable() throws SQLException{
//        boolean answer = null;
//        return answer;
//    }
}