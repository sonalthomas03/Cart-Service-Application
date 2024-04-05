
import java.sql.*;
import java.util.*;



public class UserManipulation implements IUserManipulation
{

    private Connection connection;
    Scanner scanner = new Scanner(System.in);
    
    public UserManipulation(Connection connection) {
        this.connection = connection;
    }


    public Product getProductById(int productId) throws SQLException {
        String query = "SELECT id, name, price FROM products WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                return new Product(id, name, price);
            }
        }
        return null;
    }


    public void registerUser(String username, String password) throws SQLException {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
        }
    }

    public int getUserId(String username) throws SQLException {
        int userId = -1; // Default value if user is not found
        String query = "SELECT id FROM users WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getInt("id");
            }
        }
        return userId;
    }
}
