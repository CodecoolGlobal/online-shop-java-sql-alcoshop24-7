package Controller;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import Model.Customer;
import Model.User;
import Model.Order;
import Model.Product;
import Model.Basket;
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
        int answer = admin.askAdminForActivity();
        if(answer == 1){
            List<Product> products = admin.getAllProducts();
            adminView.printAllProducts(products);
        }else if (answer == 2){
            List<User> users = admin.getAllCustomers();
            adminView.printAllCustomers(users);
        }else if (answer == 3){
            List<Product> products = admin.getAllProducts();
            int productID = adminView.getIntAnswer("Enter product ID: ");
            int quantiti = adminView.getIntAnswer("Enter new stock size: ");
            if (productID -1 != products.size()){
                admin.refillTheStock(products.get(productID-1), quantiti);
            }else {
                System.out.println("no such item in the stock");
            }
        }else if(answer == 4){
            System.out.println("developer was lazy, no adding product for now");

            String name = adminView.getStringAnswer("Enter name of product");
            int typeId = adminView.getIntAnswer("Enter type id (number between 1 and 6): ");
            float price = adminView.getFloatAnswer("Enter price separated by dot: ");
            int amount = adminView.getIntAnswer("Enter amount of the product in stock");

            String available = "true";
            int rate = adminView.getIntAnswer("What is the rate");

            Product product = new Product(0, name, typeId, price, amount, available, rate );
            admin.CreateProduct(product);

        }else if(answer == 5){
            List<Order> orders = admin.getAllOrders();
            adminView.printAllOrders(orders);
        }else if(answer == 6){
            adminConsoleHandler = false;
        } else {
            System.out.println("Enter valid number!");
        }
    }

    private void handleCustomer() throws SQLException{
        Object object = mainCotrillerDao.getAllUsers().get(1);
        Customer currentCustomer = (Customer) object;


        customerView.printMenu();
        int answer = customerView.getIntAnswer("Choose option from menu");

        if (answer == 1){
            customerView.printProducts(customerController.getAllProducts());
            //System.out.println(currentCustomer.getId());
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
        else if (answer ==6){
            customerController.getAllProducts();
            int chosenProductID = customerView.getIntAnswer("Choose ID product to buy");
            int ammount = customerView.getIntAnswer("How much you want?");
            Map<Product, Integer> mapForBasket = new HashMap<>();
            mapForBasket.put(customerController.getProductById(chosenProductID), ammount);
            Basket yourBasket = new Basket(customerController.getOrderSize(), mapForBasket);
            Order ourOrder = new Order(0, currentCustomer.getId(), 0, "pending",
                    LocalDateTime.now(), yourBasket);
            customerController.addOrder(ourOrder);
        }
        else if (answer == 5){
            int chosenProductID = customerView.getIntAnswer("Which product you want to rate? Choose ID");
            int customersRate = customerView.getIntAnswer("Your rate is: (form 1 to 5)");
            customerController.rateProduct(chosenProductID, customersRate);
        }


    }

}