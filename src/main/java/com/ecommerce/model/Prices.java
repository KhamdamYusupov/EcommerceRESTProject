package com.ecommerce.model;

public class Prices {
    private int priceId;
    private int priceValue;
    private String currency;
    private int productId;

    public Prices() {
    }

    public Prices(int priceId, int priceValue, String currency, int productId) {
        this.priceId = priceId;
        this.priceValue = priceValue;
        this.currency = currency;
        this.productId = productId;
    }

    public int getPriceId() {
        return priceId;
    }

    public void setPriceId(int priceId) {
        this.priceId = priceId;
    }

    public int getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(int priceValue) {
        this.priceValue = priceValue;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "Prices{" +
                "priceId=" + priceId +
                ", priceValue=" + priceValue +
                ", currency='" + currency + '\'' +
                ", productId=" + productId +
                '}';
    }
}
