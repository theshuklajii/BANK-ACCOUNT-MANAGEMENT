package com.shopease.models;

import java.math.BigDecimal;

public class CartItem {
    private int productId;
    private String productName;
    private int quantity;
    private BigDecimal price;

    public CartItem(int productId, String productName, int quantity, BigDecimal price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters & Setters --------------------------------------------
    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public BigDecimal getPrice() { return price; }

    public BigDecimal getSubTotal() { return price.multiply(new BigDecimal(quantity)); }
}