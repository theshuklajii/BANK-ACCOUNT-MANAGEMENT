package com.shopease.dao;

import com.shopease.models.Order;
import com.shopease.models.OrderItem;
import com.shopease.models.Product;
import com.shopease.util.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    
    public int createOrder(Order order) {
        String sql = "INSERT INTO orders (user_id, total_amount, order_date, status, shipping_address, customer_name, customer_phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setInt(1, order.getUserId());
            statement.setBigDecimal(2, order.getTotalAmount());
            statement.setTimestamp(3, order.getOrderDate());
            statement.setString(4, order.getStatus());
            statement.setString(5, order.getShippingAddress());
            statement.setString(6, order.getCustomerName());
            statement.setString(7, order.getCustomerPhone());
            
            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error creating order: " + e.getMessage());
        }
        
        return -1; // Return -1 if order creation failed
    }
    
    public boolean createOrderItem(OrderItem orderItem) {
        String sql = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, orderItem.getOrderId());
            statement.setInt(2, orderItem.getProductId());
            statement.setInt(3, orderItem.getQuantity());
            statement.setBigDecimal(4, orderItem.getPrice());
            
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error creating order item: " + e.getMessage());
            return false;
        }
    }
    
    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                Order order = mapResultSetToOrder(resultSet);
                order.setOrderItems(getOrderItems(orderId));
                return order;
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting order by ID: " + e.getMessage());
        }
        
        return null;
    }
    
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Order order = mapResultSetToOrder(resultSet);
                order.setOrderItems(getOrderItems(order.getOrderId()));
                orders.add(order);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting orders by user ID: " + e.getMessage());
        }
        
        return orders;
    }
    
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY order_date DESC";
        
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            
            while (resultSet.next()) {
                Order order = mapResultSetToOrder(resultSet);
                order.setOrderItems(getOrderItems(order.getOrderId()));
                orders.add(order);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting all orders: " + e.getMessage());
        }
        
        return orders;
    }
    
    public List<OrderItem> getOrderItems(int orderId) {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT oi.*, p.name, p.description, p.category, p.stock_quantity, p.image_url " +
                    "FROM order_items oi JOIN products p ON oi.product_id = p.product_id WHERE oi.order_id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                OrderItem orderItem = mapResultSetToOrderItem(resultSet);
                Product product = mapResultSetToProduct(resultSet);
                orderItem.setProduct(product);
                orderItems.add(orderItem);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting order items: " + e.getMessage());
        }
        
        return orderItems;
    }
    
    public boolean updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE order_id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, status);
            statement.setInt(2, orderId);
            
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating order status: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deleteOrder(int orderId) {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);
            
            // Delete order items first
            String deleteItemsSql = "DELETE FROM order_items WHERE order_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(deleteItemsSql)) {
                statement.setInt(1, orderId);
                statement.executeUpdate();
            }
            
            // Delete order
            String deleteOrderSql = "DELETE FROM orders WHERE order_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(deleteOrderSql)) {
                statement.setInt(1, orderId);
                int rowsAffected = statement.executeUpdate();
                
                if (rowsAffected > 0) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error deleting order: " + e.getMessage());
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("Error rolling back transaction: " + rollbackEx.getMessage());
                }
            }
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }
    
    public List<Order> getOrdersByStatus(String status) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE status = ? ORDER BY order_date DESC";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, status);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Order order = mapResultSetToOrder(resultSet);
                order.setOrderItems(getOrderItems(order.getOrderId()));
                orders.add(order);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting orders by status: " + e.getMessage());
        }
        
        return orders;
    }
    
    public BigDecimal getTotalRevenue() {
        String sql = "SELECT SUM(total_amount) FROM orders WHERE status != 'Cancelled'";
        
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            
            if (resultSet.next()) {
                BigDecimal revenue = resultSet.getBigDecimal(1);
                return revenue != null ? revenue : BigDecimal.ZERO;
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting total revenue: " + e.getMessage());
        }
        
        return BigDecimal.ZERO;
    }
    
    public int getTotalOrderCount() {
        String sql = "SELECT COUNT(*) FROM orders";
        
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting total order count: " + e.getMessage());
        }
        
        return 0;
    }
    
    private Order mapResultSetToOrder(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setOrderId(resultSet.getInt("order_id"));
        order.setUserId(resultSet.getInt("user_id"));
        order.setTotalAmount(resultSet.getBigDecimal("total_amount"));
        order.setOrderDate(resultSet.getTimestamp("order_date"));
        order.setStatus(resultSet.getString("status"));
        order.setShippingAddress(resultSet.getString("shipping_address"));
        order.setCustomerName(resultSet.getString("customer_name"));
        order.setCustomerPhone(resultSet.getString("customer_phone"));
        return order;
    }
    
    private OrderItem mapResultSetToOrderItem(ResultSet resultSet) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderItemId(resultSet.getInt("order_item_id"));
        orderItem.setOrderId(resultSet.getInt("order_id"));
        orderItem.setProductId(resultSet.getInt("product_id"));
        orderItem.setQuantity(resultSet.getInt("quantity"));
        orderItem.setPrice(resultSet.getBigDecimal("price"));
        return orderItem;
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