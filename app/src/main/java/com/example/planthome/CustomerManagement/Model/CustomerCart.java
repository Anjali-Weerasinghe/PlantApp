package com.example.planthome.CustomerManagement.Model;

public class CustomerCart {

    String TotalPrice;

    public CustomerCart() {

    }

    public CustomerCart(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }
}
