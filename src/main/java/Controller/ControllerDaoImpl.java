package Controller;

import Model.Customer;
import Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ControllerDaoImpl implements ControllerDao {


    @Override
    public int getUserType(String login, String password) throws SQLException {
        List allUsers = new ArrayList<>();
        int userType = 0;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:alcoshop2.db");
            Statement stmt = connection.createStatement();
            connection.setAutoCommit(false);
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM Users;");
            while (resultSet.next()){
                int userId = resultSet.getInt("ID");
                int userTypeId = resultSet.getInt("UserTypeID");
                String userLogin = resultSet.getString("Login");
                String userPass = resultSet.getString("Password");
                User user = new Customer(userId, userTypeId, userLogin, userPass);
                if(user.getLogin().equals(login) && user.getPassword().equals(password)){
                    userType = userTypeId;
                }
                allUsers.add(user);
            }
            resultSet.close();
            stmt.close();
            connection.commit();
            connection.close();
        } catch (ClassNotFoundException ex){
            System.err.println(ex.getMessage());
        }
        return userType;
    }
}
