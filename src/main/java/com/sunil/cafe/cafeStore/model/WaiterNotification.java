package com.sunil.cafe.cafeStore.model;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WaiterNotification {

    private Order order;
    private CafeTable table;
    private String message;
    private LocalDateTime time;
}
