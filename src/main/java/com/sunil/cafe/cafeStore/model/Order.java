package com.sunil.cafe.cafeStore.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("orders")
@Data
public class Order {

    @Id
    private String id;

    private String cafeId;

    private int personCount;

    private List<OrderItem> items;

    private double totalAmount;

    private String tableId;
    private String waiterId;

    private boolean ready=false;

    private OrderStatus status; // CREATED, SERVED, COMPLETED

    private LocalDateTime createdAt;
}

