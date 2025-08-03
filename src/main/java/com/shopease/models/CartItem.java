package com.shopease.models;

import java.math.BigDecimal;

public class CartItem {
    private int cartItemId;
    private int userId;
    private int productId;
    private int quantity;
    private Product product; // For displaying product details
    
    // Default constructor
    public CartItem() {}
    
    // Constructor with all fields
    public CartItem(int cartItemId, int userId, int productId, int quantity) {
        this.cartItemId = cartItemId;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }
    
    // Constructor without cartItemId (for new cart items)
    public CartItem(int userId, int productId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }
    
    // Constructor with product object
    public CartItem(int userId, Product product, int quantity) {
        this.userId = userId;
        this.productId = product.getProductId();
        this.quantity = quantity;
        this.product = product;
    }
    
    // Getters and Setters
    public int getCartItemId() {
        return cartItemId;
    }
    
    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public int getProductId() {
        return productId;
    }
    
    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public Product getProduct() {
        return product;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
    
    // Calculate total price for this cart item
    public BigDecimal getTotalPrice() {
        if (product != null) {
            return product.getPrice().multiply(BigDecimal.valueOf(quantity));
        }
        return BigDecimal.ZERO;
    }
    
    @Override
    public String toString() {
        return "CartItem{" +
                "cartItemId=" + cartItemId +
                ", userId=" + userId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", totalPrice=" + getTotalPrice() +
                '}';
    }
}