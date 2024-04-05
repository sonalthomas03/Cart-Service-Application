import java.sql.*;
import java.util.*;

public interface ICartManipulation
{
    void removeItemFromCart(int userId) throws SQLException;
    void clearCart(int userId) throws SQLException;
    void addItemToCart(int userId, CartManipulation cartmanipulation,Display displaY,UserManipulation usermanipulation) throws SQLException;
    void billCalculation(int userId, CartManipulation cartmanipulation) throws SQLException;
}