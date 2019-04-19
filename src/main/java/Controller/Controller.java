package Controller;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
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

    public void handleShop() throws SQLException{

        String login = adminView.getStringAnswer("Enter your login: ");
        String password = adminView.getStringAnswer("Enter your password: ");
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
            float alcoholContent = adminView.getFloatAnswer("Enter vol% of product: ");
            float volume = adminView.getFloatAnswer("Enter quantity pf the product(for example 0.5 L): ");
            int amount = adminView.getIntAnswer("Enter amount of the product in stock");
            int year = adminView.getIntAnswer("Enter year (exp date yyyy): ");
            int month = adminView.getIntAnswer("Enter month (exp date mm): ");;
            int day = adminView.getIntAnswer("Enter day (exp date dd): ");;
            Date expirationDate = new Date(year, month, day);
            Product product = new Product(0, name, typeId, price, alcoholContent, volume, amount, expirationDate);
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

    public void handleCustomer() throws SQLException{
        customerView.printMenu();
        int answer = customerView.getIntAnswer("Choose option from menu");

        if (answer == 1){
            customerView.printProducts(customerController.getAllProducts());
        }
//        else if(answer == 2){
//            customerView.printOrders(customerController.getOrders());
        //potrzeba jakos dostac sie do id customera z poziomu controllera
        //}
        else if (answer == 3){
            int chosenID = customerView.getIntAnswer("Choose ID to see product");
            customerView.printProduct(customerController.getProductById(chosenID));
        }


    }

}