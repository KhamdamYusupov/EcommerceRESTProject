package com.ecommerce.model;

public class OrderItemView {
    private int id;
    private int productId;
    private int quantity;
    private int userId;
    private String productName;
    private int categoryId;

    public OrderItemView() {
    }

    public OrderItemView(int id, int productId, int quantity, int userId,
                         String productName, int categoryId) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.userId = userId;
        this.productName = productName;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "OrderItemView{" +
                "id=" + id +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", userId=" + userId +
                ", productName='" + productName + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }
}
