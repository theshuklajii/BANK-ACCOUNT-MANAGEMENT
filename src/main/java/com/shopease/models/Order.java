package com.shopease.models;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class Order {
    private int orderId;
    private int userId;
    private BigDecimal totalAmount;
    private Timestamp orderDate;
    private String status; // "Pending", "Shipped", "Delivered", "Cancelled"
    private String shippingAddress;
    private String customerName;
    private String customerPhone;
    private List<OrderItem> orderItems;
    
    // Default constructor
    public Order() {}
    
    // Constructor with all fields
    public Order(int orderId, int userId, BigDecimal totalAmount, Timestamp orderDate, 
                 String status, String shippingAddress, String customerName, String customerPhone) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
    }
    
    // Constructor without orderId (for new orders)
    public Order(int userId, BigDecimal totalAmount, String status, 
                 String shippingAddress, String customerName, String customerPhone) {
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.orderDate = new Timestamp(System.currentTimeMillis());
    }
    
    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }
    
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public Timestamp getOrderDate() {
        return orderDate;
    }
    
    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getShippingAddress() {
        return shippingAddress;
    }
    
    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getCustomerPhone() {
        return customerPhone;
    }
    
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
    
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    
    public boolean isPending() {
        return "Pending".equals(this.status);
    }
    
    public boolean isShipped() {
        return "Shipped".equals(this.status);
    }
    
    public boolean isDelivered() {
        return "Delivered".equals(this.status);
    }
    
    public boolean isCancelled() {
        return "Cancelled".equals(this.status);
    }
    
    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", totalAmount=" + totalAmount +
                ", orderDate=" + orderDate +
                ", status='" + status + '\'' +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}