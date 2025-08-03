package com.shopease.dao;

import com.shopease.models.Order;
import com.shopease.models.OrderItem;
import java.sql.*;
import java.util.List;

public class OrderDAO {
    public int createOrder(Order order, List<OrderItem> items) throws SQLException {
        String orderSQL = "INSERT INTO orders(user_id, total_amount, address) VALUES(?,?,?)";
        String itemSQL  = "INSERT INTO order_items(order_id, product_id, quantity, price) VALUES(?,?,?,?)";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement psOrder = conn.prepareStatement(orderSQL, Statement.RETURN_GENERATED_KEYS);
            psOrder.setInt(1, order.getUserId());
            psOrder.setBigDecimal(2, order.getTotalAmount());
            psOrder.setString(3, order.getAddress());
            psOrder.executeUpdate();

            ResultSet rs = psOrder.getGeneratedKeys();
            if (rs.next()) order.setId(rs.getInt(1));

            PreparedStatement psItem = conn.prepareStatement(itemSQL);
            for (OrderItem it : items) {
                psItem.setInt(1, order.getId());
                psItem.setInt(2, it.getProductId());
                psItem.setInt(3, it.getQuantity());
                psItem.setBigDecimal(4, it.getPrice());
                psItem.addBatch();
            }
            psItem.executeBatch();
            conn.commit();
            return order.getId();
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
            if (conn != null) conn.close();
        }
    }

    public boolean updateStatus(int orderId, String status) {
        String sql = "UPDATE orders SET status=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}