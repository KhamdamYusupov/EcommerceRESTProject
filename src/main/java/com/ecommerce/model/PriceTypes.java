package com.ecommerce.model;

public class PriceTypes {
    private int priceTypeId;
    private String priceTypeName;

    public PriceTypes() {
    }

    public PriceTypes(int priceTypeId, String priceTypeName) {
        this.priceTypeId = priceTypeId;
        this.priceTypeName = priceTypeName;
    }

    public int getPriceTypeId() {
        return priceTypeId;
    }

    public void setPriceTypeId(int priceTypeId) {
        this.priceTypeId = priceTypeId;
    }

    public String getPriceTypeName() {
        return priceTypeName;
    }

    public void setPriceTypeName(String priceTypeName) {
        this.priceTypeName = priceTypeName;
    }

    @Override
    public String toString() {
        return "PriceTypes{" +
                "priceTypeId=" + priceTypeId +
                ", priceTypeName='" + priceTypeName + '\'' +
                '}';
    }
}
