package com.ecommerce.model;

import java.util.List;

public class Cart {
    private int cartId;
    private List<Product> productsInCart;

    public Cart() {
    }

    public Cart(List<Product> products) {
        this.productsInCart = products;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public List<Product> getProductsInCart() {
        return productsInCart;
    }

    public void setProductsInCart(List<Product> productsInCart) {
        this.productsInCart = productsInCart;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + cartId +
                ", products=" + productsInCart +
                '}';
    }
}
