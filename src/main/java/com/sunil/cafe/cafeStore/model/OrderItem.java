package com.sunil.cafe.cafeStore.model;


import lombok.Data;

@Data
public class OrderItem {
    private String menuItemId;
    private String name;
    private double price;
    private int quantity;
}