
import java.sql.*;
import java.util.*;

public class CartManipulation implements ICartManipulation
{

    private Connection connection;
    Scanner scanner = new Scanner(System.in);
    
    public CartManipulation(Connection connection) {
        this.connection = connection;
    }


    public void removeItemFromCart(int userId) throws SQLException {

        System.out.println("Enter the id of item you wish to delete: ");
        int itemId = scanner.nextInt();
        String query = "DELETE FROM cart_items WHERE user_id = ? AND id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, itemId);
            preparedStatement.executeUpdate();
        }

        System.out.println("\nItem deleted successfully!\n");

    }

    public void clearCart(int userId) throws SQLException {
        String query = "DELETE FROM cart_items WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        }
        System.out.println("\nCart cleared successfully!\n");
    }


    public void addItemToCart(int userId, CartManipulation cartmanipulation,Display displaY,UserManipulation usermanipulation) throws SQLException {
        CartManipulation cartManipulation = cartmanipulation;
        Display display = displaY;
        display.availableProducts();

        System.out.println("\nEnter the product id of the item you wish to add: ");
        int ProdId = scanner.nextInt();
        System.out.println("Enter the quantity you wish to add: ");
        int quantity = scanner.nextInt();

        Product product = usermanipulation.getProductById(ProdId);

        String query = "INSERT INTO cart_items (user_id, product_id, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, product.getId());
            preparedStatement.setInt(3, quantity);
            preparedStatement.executeUpdate();
        }
        System.out.println("\nItem added to cart successfully!\n\n");
    }


    public void billCalculation(int userId, CartManipulation cartmanipulation) throws SQLException {
        double totalBill = 0.0;
        CartManipulation cartManipulation = cartmanipulation;
    
        String query = "SELECT p.price AS product_price, ci.quantity " +
                       "FROM cart_items ci " +
                       "JOIN products p ON ci.product_id = p.id " +
                       "WHERE ci.user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Your cart is empty, cannot calculate total.");
                return; // Exit the method
            }
            while (resultSet.next()) {
                double productPrice = resultSet.getDouble("product_price");
                int quantity = resultSet.getInt("quantity");
                double itemTotal = productPrice * quantity;
                totalBill += itemTotal;
            }
        }
    
        System.out.println("TOTAL: "+totalBill);

        System.out.println("Would you like to pay now?(yes/no)");
        String pay = scanner.nextLine();
        if(pay.equals("yes"))
        {
            System.out.println("Payment successful! Your cart is now clear.");
            cartManipulation.clearCart(userId);
        }
    }
}