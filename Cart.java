import java.sql.*;
import java.util.*;

public class Cart {
    private int id;
    private List<CartItem> items;

    public Cart(int id) {
        this.id = id;
        this.items = new ArrayList<>();
    }

    public void addItem(Product product, int quantity) {
        // Create a new CartItem and add it to the cart
        int itemId = items.size() + 1; // Generate unique item ID
        CartItem newItem = new CartItem(itemId, product, quantity);
        items.add(newItem);
    }

    public void removeItem(CartItem item) {
        items.remove(item);
    }

    public void clearCart() {
        items.clear();
    }

    public List<CartItem> getItems() {
        return items;
    }
}