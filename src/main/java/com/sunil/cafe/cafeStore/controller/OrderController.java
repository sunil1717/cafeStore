package com.sunil.cafe.cafeStore.controller;

import com.sunil.cafe.cafeStore.model.Order;
import com.sunil.cafe.cafeStore.model.OrderItem;
import com.sunil.cafe.cafeStore.model.OrderStatus;
import com.sunil.cafe.cafeStore.model.personCount;
import com.sunil.cafe.cafeStore.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }






    //calculate amount from list of oredr items

    @PostMapping("/calculate-amount")

    public Map<String, Object> initiateOrder (@RequestBody List<OrderItem> orderItems){
        return orderService.initiateOrder(orderItems);
    }


    // Place order (customer machine)
    @PostMapping("/place-order")
    public Map<String, Object> placeOrder(@RequestBody personCount personcount) {
        return orderService.placeOrderAfterPayment(personcount.getOrderId(), personcount.getPeopleCount());
    }

    // Food served (waiter action)
    @PutMapping("/{orderId}/served")
    public Map<String, Object> serveOrder(@PathVariable String orderId) {
       return orderService.markOrderServed(orderId);
    }

    // Customer leaves & complete order (waiter/system action)
    @PutMapping("/{orderId}/complete")
    public Map<String, Object> completeOrder(@PathVariable String orderId) {
       return orderService.completeOrder(orderId);
    }

    @GetMapping("/{waiterId}")
    public  List<Order> findOrderbyWaiterID(@PathVariable String waiterId){
        return  orderService.findOrder(waiterId);
    }


    @GetMapping
    public  List<Order> findOrderbyWaiterIDAndStatus(@RequestParam String waiterId, @RequestParam OrderStatus status){
        return  orderService.findOrderStatusWithId(waiterId,status);
    }
    @GetMapping("/bystatus")
    public  List<Order> findOrderbyStatus(@RequestParam OrderStatus status){
        return  orderService.findOrderByStatus(status);
    }

    @PostMapping("/notify")
    public  void notifyWaiter(@RequestBody Order ord){
           orderService.notifyWaiter(ord);
    }



}


