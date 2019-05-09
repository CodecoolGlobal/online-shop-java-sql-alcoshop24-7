package Controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import Controller.CustomerDAO;
import Model.Product;
import Model.Basket;
import Model.Order;
import sun.misc.Resource;

import java.sql.*;
import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class CustomerDAOImpl implements CustomerDAO {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private List<Product> allProducts;
    private List<Order> myOrders;

    @Override
    public List<Product> getAllProducts() throws SQLException {
        allProducts = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:alcoshop2.db");
            Statement stmt = connection.createStatement();
            connection.setAutoCommit(false);

            ResultSet resultSet = stmt.executeQuery("SELECT * FROM Products;");

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("Name");
                int typeID = resultSet.getInt("TypeID");
                float price = resultSet.getFloat("Price");
                int amount = resultSet.getInt("Amount");
                int rate = resultSet.getInt("Rate");
                String available = resultSet.getString("Available");
                Product product = new Product(id, name, typeID, price, amount, available, rate);


                allProducts.add(product);
            }

            stmt.close();
            connection.commit();
            connection.close();
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        return allProducts;
    }






    @Override
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
                String status = resultSet.getString("Status");
                LocalDateTime creationDate = LocalDateTime.parse(resultSet.getString("CreationDate"), formatter);
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(creationDate);
                Map idSet = getProductsIDByBasketID(basketID);

                Map products = getBasket(idSet);
                Basket basket = new Basket(basketID, products);
                myOrders.add(new Order(orderID, userID, basketID, status ,creationDate, basket));}

            con.commit();
            con.close();

        } catch (SQLException  ex) {
            System.err.println(ex.getMessage());
        }

        return myOrders;
    }

    @Override
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
            String status = resSet.getString("Status");
            LocalDateTime creationDate = LocalDateTime.parse(resSet.getString("CreationDate"), formatter);
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(creationDate);
            Map idSet = getProductsIDByBasketID(basketID);
            Map products = getBasket(idSet);
            Basket basket = new Basket(basketID, products);
            order = new Order(orderID, userID, basketID, status, creationDate, basket);

            conn.commit();
            conn.close();

        } catch (SQLException  ex ) {
            System.err.println(ex.getMessage());
        }
        return order;
    }

    @Override
    public Product getProductById(int productID) {
        Product product = null;
        Connection connection = setConnection();
        ResultSet resultSet2 = null;

        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Products WHERE id=?");
            statement.setInt(1, productID);
            resultSet2 = statement.executeQuery();

            int id = resultSet2.getInt("ID");
            String name = resultSet2.getString("Name");
            int typeID = resultSet2.getInt("TypeID");
            float price = resultSet2.getFloat("Price");
            int amount = resultSet2.getInt("Amount");
            int rate = resultSet2.getInt("Rate");
            String available = resultSet2.getString("Available");
            product = new Product(id, name, typeID, price,
                     amount, available, rate );


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

//    TODO
    @Override
    public void addNewOrder(Order order) {
        updateOrderTable(order);
        updateBasketProductTable(order.getBasket());
        updateBasketTable(order.getBasket());
    }

    private Map<Product, Integer> getBasket(Map<Integer, Integer> productIDAmount){
        Map<Product, Integer> products = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : productIDAmount.entrySet()) {
            Product product = getProductById(entry.getKey());
            products.put(product, entry.getValue());
        }
        return products;
    }

    private Map getProductsIDByBasketID(int basketID){

        Map<Integer, Integer> products = new HashMap();
        Connection connection = setConnection();
        ResultSet resultSet2 = null;

        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `Basket_Products` WHERE BasketID=?");
            statement.setInt(1, basketID);

            resultSet2 = statement.executeQuery();
            while(resultSet2.next()) {
                int productsID = resultSet2.getInt("ProductID");
                int amount = resultSet2.getInt("Ammount");
                products.put(productsID, amount);

            }
            statement.close();
            connection.close();

        } catch (Exception exception) {
            System.err.println(exception);

        }
        return products;

    }

    private Connection setConnection() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:alcoshop2.db");
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return connection;
    }

//    TODO
    private void updateOrderTable(Order order){
        try {Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:alcoshop2.db");
            PreparedStatement stmt;
            connection.setAutoCommit(false);
            String sqlStatments = "insert into Orders"
                    + "(ID, CustomerID, BasketID, Status, CreationDate)"
                    + " values (?, ? ,? ,? ,? )";

            stmt = connection.prepareStatement(sqlStatments);

            LocalDate date = order.getCreationDate().toLocalDate();
            java.sql.Date sqlDate = java.sql.Date.valueOf(date);

            stmt.setInt(1, getOrderSize());
            stmt.setInt(2, order.getCustomerID());
            stmt.setInt(3, order.getBasketID());
            stmt.setString(4, order.getStatus());
            stmt.setDate(5, sqlDate);

            stmt.executeUpdate();

            stmt.close();
            connection.commit();
            connection.close();


        }catch (ClassNotFoundException | SQLException ex){
            System.err.println(ex.getMessage());
        }
    }

    private void updateBasketTable(Basket basket){
        try {Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:alcoshop2.db");
            PreparedStatement stmt;
            connection.setAutoCommit(false);
            String sqlStatments = "insert into Baskets"
                    + "(BasketID, OrderID)"
                    + " values (?, ?)";

            stmt = connection.prepareStatement(sqlStatments);

            stmt.setInt(1, basket.getID());
            stmt.setInt(2, basket.getID());


            stmt.executeUpdate();

            stmt.close();
            connection.commit();
            connection.close();


        }catch (ClassNotFoundException | SQLException ex){
            System.err.println(ex.getMessage());
        }
    }
    private void updateBasketProductTable(Basket basket){}

    private int getOrderSize(){
        int orderSize = 0;
        Connection con = setConnection();
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            con.setAutoCommit(false);
            stmt = con.prepareStatement("SELECT * FROM Orders");


            resultSet = stmt.executeQuery();

            while(resultSet.next()) {
                orderSize ++;
            }

            con.commit();
            con.close();

        } catch (SQLException  ex) {
            System.err.println(ex.getMessage());
        }
        return orderSize;
    }
}