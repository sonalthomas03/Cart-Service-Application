
import java.sql.*;
import java.util.*;






public class Display implements IDisplay
{

    private Connection connection;
    Scanner scanner = new Scanner(System.in);
    
    public Display(Connection connection) {
        this.connection = connection;
    }

    public void displayCart(int userId) throws SQLException {
        System.out.println("Your cart items:\n");
        String query = "SELECT ci.id, p.name AS product_name, p.price AS product_price, ci.quantity " +
                       "FROM cart_items ci " +
                       "JOIN products p ON ci.product_id = p.id " +
                       "WHERE ci.user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Your cart is empty.");
                return; // Exit the method
            }

            System.out.printf("%-5s %-20s %-10s %-10s\n", "ID", "Product Name", "Price", "Quantity");
            System.out.println("----------------------------------------------------");
            while (resultSet.next()) {
                int itemId = resultSet.getInt("id");
                String productName = resultSet.getString("product_name");
                double productPrice = resultSet.getDouble("product_price");
                int quantity = resultSet.getInt("quantity");
                System.out.printf("%-5d %-20s %-10.2f %-10d\n", itemId, productName, productPrice, quantity);
            }
        }
    }

    public void availableProducts() throws SQLException {

        System.out.println("\nThese are our available products:\n");
        String query = "SELECT * FROM products";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            System.out.printf("%-5s %-20s %-10s\n", "ID", "Name", "Price");
            System.out.println("--------------------------------------------");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                System.out.printf("%-5d %-20s %-10.2f\n", id, name, price);
            }
        }
    }

}