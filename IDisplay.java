import java.sql.*;
import java.util.*;


interface IDisplay
{
    void displayCart(int userId) throws SQLException;
    void availableProducts() throws SQLException;
}