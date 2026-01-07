package com.sunil.cafe.cafeStore.controller;

import com.sunil.cafe.cafeStore.model.LoginRequest;
import com.sunil.cafe.cafeStore.model.Waiter;
import com.sunil.cafe.cafeStore.model.WaiterStatus;
import com.sunil.cafe.cafeStore.service.WaiterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/waiters")
public class WaiterController {


    private final WaiterService waiterService;


    public WaiterController(WaiterService waiterService) {
        this.waiterService = waiterService;
    }


    @PostMapping
    public Waiter add(@RequestBody Waiter waiter) {
        return waiterService.addWaiter(waiter);
    }


    @GetMapping("/all")
    public List<Waiter> waiters() {
        return waiterService.getWaiters();
    }

    @PostMapping("/login")
    public Map<String,Object> login (@RequestBody LoginRequest req){
        return waiterService.login(req.getUsername() , req.getPassword());
    }

    @PutMapping("/logout/{id}")
    public Map<String,Object> logout(@PathVariable String id){
        return waiterService.logoutById(id);
    }




    @DeleteMapping("/remove/{id}")
    public void remove(@PathVariable String id){
        waiterService.removeById(id);
    }

    @GetMapping("/free")
    public  List<Waiter> getAllFreeWaiter(){
        return  waiterService.getAllFreeWaiter(WaiterStatus.FREE);
    }
}
