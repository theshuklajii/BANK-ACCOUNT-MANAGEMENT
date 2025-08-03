package com.shopease.dao;

import com.shopease.models.CartItem;
import com.shopease.models.Product;
import com.shopease.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    private ProductDAO productDAO = new ProductDAO();
    
    public boolean addToCart(int userId, int productId, int quantity) {
        // Check if item already exists in cart
        CartItem existingItem = getCartItem(userId, productId);
        
        if (existingItem != null) {
            // Update quantity if item already exists
            return updateCartItemQuantity(existingItem.getCartItemId(), existingItem.getQuantity() + quantity);
        } else {
            // Add new item to cart
            String sql = "INSERT INTO cart (user_id, product_id, quantity) VALUES (?, ?, ?)";
            
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {
                
                statement.setInt(1, userId);
                statement.setInt(2, productId);
                statement.setInt(3, quantity);
                
                int rowsAffected = statement.executeUpdate();
                return rowsAffected > 0;
                
            } catch (SQLException e) {
                System.err.println("Error adding to cart: " + e.getMessage());
                return false;
            }
        }
    }
    
    public CartItem getCartItem(int userId, int productId) {
        String sql = "SELECT * FROM cart WHERE user_id = ? AND product_id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, userId);
            statement.setInt(2, productId);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return mapResultSetToCartItem(resultSet);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting cart item: " + e.getMessage());
        }
        
        return null;
    }
    
    public List<CartItem> getCartItems(int userId) {
        List<CartItem> cartItems = new ArrayList<>();
        String sql = "SELECT c.*, p.name, p.description, p.price, p.category, p.stock_quantity, p.image_url " +
                    "FROM cart c JOIN products p ON c.product_id = p.product_id WHERE c.user_id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                CartItem cartItem = mapResultSetToCartItem(resultSet);
                Product product = mapResultSetToProduct(resultSet);
                cartItem.setProduct(product);
                cartItems.add(cartItem);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting cart items: " + e.getMessage());
        }
        
        return cartItems;
    }
    
    public boolean updateCartItemQuantity(int cartItemId, int quantity) {
        if (quantity <= 0) {
            return removeCartItem(cartItemId);
        }
        
        String sql = "UPDATE cart SET quantity = ? WHERE cart_item_id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, quantity);
            statement.setInt(2, cartItemId);
            
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating cart item quantity: " + e.getMessage());
            return false;
        }
    }
    
    public boolean removeCartItem(int cartItemId) {
        String sql = "DELETE FROM cart WHERE cart_item_id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, cartItemId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error removing cart item: " + e.getMessage());
            return false;
        }
    }
    
    public boolean removeCartItemByProduct(int userId, int productId) {
        String sql = "DELETE FROM cart WHERE user_id = ? AND product_id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, userId);
            statement.setInt(2, productId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error removing cart item by product: " + e.getMessage());
            return false;
        }
    }
    
    public boolean clearCart(int userId) {
        String sql = "DELETE FROM cart WHERE user_id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, userId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected >= 0; // Return true even if no items were deleted
            
        } catch (SQLException e) {
            System.err.println("Error clearing cart: " + e.getMessage());
            return false;
        }
    }
    
    public int getCartItemCount(int userId) {
        String sql = "SELECT COUNT(*) FROM cart WHERE user_id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting cart item count: " + e.getMessage());
        }
        
        return 0;
    }
    
    public int getTotalCartQuantity(int userId) {
        String sql = "SELECT SUM(quantity) FROM cart WHERE user_id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting total cart quantity: " + e.getMessage());
        }
        
        return 0;
    }
    
    private CartItem mapResultSetToCartItem(ResultSet resultSet) throws SQLException {
        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(resultSet.getInt("cart_item_id"));
        cartItem.setUserId(resultSet.getInt("user_id"));
        cartItem.setProductId(resultSet.getInt("product_id"));
        cartItem.setQuantity(resultSet.getInt("quantity"));
        return cartItem;
    }
    
    private Product mapResultSetToProduct(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setProductId(resultSet.getInt("product_id"));
        product.setName(resultSet.getString("name"));
        product.setDescription(resultSet.getString("description"));
        product.setPrice(resultSet.getBigDecimal("price"));
        product.setCategory(resultSet.getString("category"));
        product.setStockQuantity(resultSet.getInt("stock_quantity"));
        product.setImageUrl(resultSet.getString("image_url"));
        return product;
    }
}