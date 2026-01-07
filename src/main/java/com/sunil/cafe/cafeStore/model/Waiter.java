package com.sunil.cafe.cafeStore.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("waiters")
@Data
public class Waiter {

    @Id
    private String id;

    private String cafeId;

    private String name;

    @Indexed(unique = true)
    private String username;

    private String password;

    private WaiterStatus status;  // FREE, BUSY
    private int activeOrders;

    private boolean active = true;
}
