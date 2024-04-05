import java.sql.*;
import java.util.*;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        try {

            //DATABASE CONNECTIVITY 
            Scanner scanner = new Scanner(System.in); 
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/shopping_cart", "root", "Sonal1234");

            
            //objects for each service class
            UserManipulation userManipulation = new UserManipulation(connection);

            CartManipulation cartManipulation = new CartManipulation(connection);

            Display display = new Display(connection);
            
            System.out.println("Welcome to E-Shopping!\n");

            System.out.println("Are you a new user?(yes/no)");
            String ns = scanner.nextLine();

            if(ns.equals("yes"))
            {
                System.out.println("Enter your username: ");
                String new_username = scanner.nextLine();
                System.out.println("Enter password: ");
                String new_password = scanner.nextLine();   
                userManipulation.registerUser(new_username, new_password);
                System.out.println("Registration Successful!");
            }

            System.out.println("Username: ");
            String Curr_Username = scanner.nextLine().trim();
            System.out.println("\nWelcome, " + Curr_Username);

            int Curr_UserId = userManipulation.getUserId(Curr_Username);

            display.displayCart(Curr_UserId);
            System.out.println("");

            int cont = 0;
            while(cont!=1)
            {
                System.out.println("\n\tMENU\n");
                System.out.println("1.Add item to Cart.\n2.Remove item from cart.\n3.Clear cart\n4.View Cart\n5.Checkout - Bill Calculation.\n6.Exit\n");
                System.out.println("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        cartManipulation.addItemToCart(Curr_UserId,cartManipulation,display,userManipulation);
                        break;
                    case 2:
                        display.displayCart(Curr_UserId);
                        cartManipulation.removeItemFromCart(Curr_UserId);    
                        break;
                    case 3:
                         cartManipulation.clearCart(Curr_UserId);  
                         break;     
                    case 4:
                        display.displayCart(Curr_UserId);
                        break;
                    case 5:
                        display.displayCart(Curr_UserId);
                        cartManipulation.billCalculation(Curr_UserId,cartManipulation);
                        break;
                    case 6:
                        System.out.println("\nThankyou for shopping with us!\n");
                        cont = 1;
                        break;
                    default:
                        System.out.println("\nInvalid!\n");
                        break;
                }
            }

            scanner.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
