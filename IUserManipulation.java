
import java.sql.*;
import java.util.*;

interface IUserManipulation
{
    void registerUser(String username, String password) throws SQLException;
    Product getProductById(int productId) throws SQLException;
    int getUserId(String username) throws SQLException;
}
