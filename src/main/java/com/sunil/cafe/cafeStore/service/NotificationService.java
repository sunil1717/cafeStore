package com.sunil.cafe.cafeStore.service;


import com.sunil.cafe.cafeStore.model.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }


    public void notifyWaiter(String waiterId, Order order, CafeTable table,String msg) {

        WaiterNotification notification = new WaiterNotification();
        notification.setOrder(order);
        notification.setTable(table);
        notification.setMessage(msg);
        notification.setTime(LocalDateTime.now());

        messagingTemplate.convertAndSend(
                "/topic/waiter/" + waiterId,
                notification
        );
    }

    public void notifyKitchen(Order order , String waiterName){
        KitchenNotification ns = new KitchenNotification();
        ns.setOrder(order);
        ns.setWaiterName(waiterName);

        messagingTemplate.convertAndSend(
                "/topic/kitchen" ,ns
        );

    }




}
