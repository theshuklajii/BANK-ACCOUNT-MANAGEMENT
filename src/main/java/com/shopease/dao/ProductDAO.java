package com.shopease.dao;

import com.shopease.model.Product;
import com.shopease.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public List<Product> getProducts(String search, String category) {
        List<Product> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM products WHERE 1=1");
        if (search != null && !search.isEmpty()) {
            sql.append(" AND name LIKE ?");
        }
        if (category != null && !category.isEmpty()) {
            sql.append(" AND category = ?");
        }

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int idx = 1;
            if (search != null && !search.isEmpty()) {
                ps.setString(idx++, "%" + search + "%");
            }
            if (category != null && !category.isEmpty()) {
                ps.setString(idx, category);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getString("category")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}