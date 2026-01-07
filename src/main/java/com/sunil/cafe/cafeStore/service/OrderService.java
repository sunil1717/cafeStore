package com.sunil.cafe.cafeStore.service;

import com.sunil.cafe.cafeStore.model.*;
import com.sunil.cafe.cafeStore.repository.CafeTableRepository;
import com.sunil.cafe.cafeStore.repository.MenuItemRepository;
import com.sunil.cafe.cafeStore.repository.OrderRepository;
import com.sunil.cafe.cafeStore.repository.WaiterRepository;


import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {




    private final   NotificationService notificationService;

    private final OrderRepository orderRepository;
    private final CafeTableRepository tableRepository;
    private final WaiterRepository waiterRepository;
    private final MenuItemRepository menuItemRepository;

    public OrderService(OrderRepository orderRepository,
                        CafeTableRepository tableRepository,
                        WaiterRepository waiterRepository,
                        MenuItemRepository menuItemRepository,
                        NotificationService notificationService) {
        this.orderRepository = orderRepository;
        this.tableRepository = tableRepository;
        this.waiterRepository = waiterRepository;
        this.menuItemRepository = menuItemRepository;
        this.notificationService=notificationService;

    }


    public Map<String, Object> initiateOrder(

            List<OrderItem> orderItems
    ) {

        double totalAmount = 0.0;

        for (OrderItem item : orderItems) {
            MenuItem menuItem = menuItemRepository
                    .findById(item.getMenuItemId())
                    .orElseThrow(() -> new RuntimeException("Menu item not available"));

            totalAmount += menuItem.getPrice() * item.getQuantity();
        }

        Order order = new Order();

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.INITIATED);
        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        Map<String, Object> response = new HashMap<>();
        response.put("orderId", savedOrder.getId());
        response.put("totalAmount", totalAmount);

        return response;
    }


    // ===============================
    // 1️⃣ PLACE ORDER
    // ===============================
    public HashMap<String,Object> placeOrderAfterPayment(String orderId, int personCount) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.INITIATED) {
            throw new RuntimeException("Order not in valid state for table assignment");
        }

        order.setPersonCount(personCount);

        CafeTable table = tableRepository
                .findFirstByStatusAndCapacityGreaterThanEqualAndActiveTrueOrderByCapacityAsc(
                        TableStatus.FREE,
                        personCount
                ).orElseThrow(() -> new RuntimeException("No table available"));

        Waiter waiter = waiterRepository
                .findFirstByStatusAndActiveTrueOrderByActiveOrdersAsc(
                        WaiterStatus.FREE
                ).orElseThrow(() -> new RuntimeException("No waiter available"));

        table.setStatus(TableStatus.OCCUPIED);
        tableRepository.save(table);

        waiter.setStatus(WaiterStatus.BUSY);
        waiter.setActiveOrders(waiter.getActiveOrders() + 1);
        waiterRepository.save(waiter);

        order.setTableId(table.getId());
        order.setWaiterId(waiter.getId());
        order.setStatus(OrderStatus.CREATED);

        Order ord= orderRepository.save(order);


        // here auto notification send to that waiter ------------------------
        notificationService.notifyWaiter(ord.getWaiterId(),ord,getTable(ord.getTableId()),"New order assigned to you");

        //here notification send to kitchen ------------------------------------
        notificationService.notifyKitchen(ord,getWaiter(ord.getWaiterId()).getName());



        HashMap<String,Object> res=new HashMap<>();
        res.put("order details ",ord);
        res.put("table no",getTable(ord.getTableId()).getTableNumber());
        res.put("waiter name",getWaiter(ord.getWaiterId()).getName());

        return res;

    }


    // ===============================
    // 2️⃣ FOOD SERVED → WAITER FREE
    // ===============================
    public Map<String, Object> markOrderServed(String orderId) {

        Order order = getOrder(orderId);

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new RuntimeException("Order is not in CREATED state");
        }

        Waiter waiter = getWaiter(order.getWaiterId());

        if (waiter.getActiveOrders() > 0) {
            waiter.setActiveOrders(waiter.getActiveOrders() - 1);
        }

        if (waiter.getActiveOrders() == 0) {
            waiter.setStatus(WaiterStatus.FREE);
        }

        waiterRepository.save(waiter);

        order.setStatus(OrderStatus.SERVED);
        orderRepository.save(order);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Order served successfully");
        response.put("orderId", orderId);

        return response;
    }



    // ===============================
    // 3️⃣ ORDER COMPLETED (TABLE FREE)
    // ===============================
    public Map<String, Object> completeOrder(String orderId) {

        Order order = getOrder(orderId);

        if (order.getStatus() != OrderStatus.SERVED) {
            throw new RuntimeException("Order must be SERVED before completion");
        }

        CafeTable table = getTable(order.getTableId());

        table.setStatus(TableStatus.FREE);
        tableRepository.save(table);

        order.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Order completed and table is now free");
        response.put("orderId", orderId);
        response.put("tableId", table.getId());

        return response;
    }


    public List<Order> findOrder(String waiterId) {

        return orderRepository.findByWaiterId(waiterId);
    }

    public List<Order> findOrderStatusWithId(String waiterId, OrderStatus status) {
        return orderRepository.findByWaiterIdAndStatus(waiterId,status);
    }

    public List<Order> findOrderByStatus(OrderStatus status) {
        return orderRepository.findByStatusAndReadyFalse(status);
    }


    //after food ready notify from kitchen---------------------------

    public void notifyWaiter(Order ord) {

        ord.setReady(true);
        orderRepository.save(ord);
        notificationService.notifyWaiter(ord.getWaiterId(),ord,getTable(ord.getTableId()),"Your Order is ready");
    }



    // ===============================
    // HELPERS
    // ===============================
    private Order getOrder(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    private Waiter getWaiter(String waiterId) {
        return waiterRepository.findById(waiterId)
                .orElseThrow(() -> new RuntimeException("Waiter not found"));
    }

    private CafeTable getTable(String tableId) {
        return tableRepository.findById(tableId)
                .orElseThrow(() -> new RuntimeException("Table not found"));
    }



}


