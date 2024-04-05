import java.sql.*;
import java.util.*;

public class User {
    private int id;
    private String username;
    private String password;
    private Cart cart;

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.cart = new Cart(id); // Each user has their own cart
    }

    public int getId() {
        return id;
    }

    public Cart getCart() {
        return cart;
    }
}