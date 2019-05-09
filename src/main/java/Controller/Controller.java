package Controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.*;
import Model.Customer;
import Model.User;
import Model.Order;
import Model.Product;
import View.AdminView;
import View.CustomerView;
import java.sql.Date;



public class Controller{
    private  ControllerDaoImpl mainCotrillerDao = new ControllerDaoImpl();
    private AdminView adminView = new AdminView();
    private AdminController admin = new AdminController();
    boolean adminConsoleHandler = true;
    private CustomerController customerController = new CustomerController();
    private CustomerView customerView = new CustomerView();
    private boolean customerConsoleHandler = true;
    String login = "";
    String password = "";

    public void handleShop() throws SQLException{

        login = adminView.getStringAnswer("Enter your login: ");
        password = adminView.getStringAnswer("Enter your password: ");
        int adminType = 1;
        int customerType = 2;

        int userType = mainCotrillerDao.getUserType(login, password);

        if(userType == adminType){
            while (adminConsoleHandler){
                handleAdmin();
            }
        }else if (userType == customerType){
            while(customerConsoleHandler){handleCustomer();}
        }else {
            System.out.println("wrong login or password");
        }

    }








    private void handleAdmin() throws SQLException{
        AdminView adminView = new AdminView();
        Boolean adminControllerRunning = true;
        adminView.clearScreen();
        while (adminControllerRunning){
            adminView.printAdminMenu();
            int answer = adminView.getIntegerInput();
            switch (answer) {
                case 1:
                    admin.printAllProducts();
                    break;
                case 2:
                    admin.addNewCategory();
                    break;
                case 3:
                    admin.updateCategoryName();
                    break;
                case 4:
                    admin.deactivateProduct();
                    break;
                case 5:
                    admin.activateProduct();
                    break;
                case 6:
                    admin.addNewProduct();
                    break;
                case 7:
                    admin.deleteProduct();
                    break;
                case 8:
                    admin.editProduct();
                    break;
                case 9:
                    admin.makeDiscount();
                    break;
                case 10:
                    admin.printAllOrders();
                    break;
                case 11:
                    adminView.clearScreen();
                    adminControllerRunning = false;
                    break;
                default:
                    adminView.clearScreen();
                    System.out.println("Enter valid number.");
            }
        }
    }









    private void handleCustomer() throws SQLException{
        Customer currentCustomer = new Customer(2, 2, login, password);
        customerView.printMenu();
        int answer = customerView.getIntAnswer("Choose option from menu");

        if (answer == 1){
            customerView.printProducts(customerController.getAllProducts());
        }
        else if(answer == 2){
            List<Order> ordersToPrint = customerController.getOrders(currentCustomer.getId());
            if (ordersToPrint != null){
                customerView.printOrders(ordersToPrint);
            }
            else{
                customerView.noOrdersMessage();
            }

        }
        else if (answer == 3){
            int chosenID = customerView.getIntAnswer("Choose ID to see product");
            List<Product> listTosearch = customerController.getAllProducts();
            customerView.printProductByID(listTosearch, chosenID);
        }
        else if (answer == 4){
            customerConsoleHandler = false;
        }


    }

}