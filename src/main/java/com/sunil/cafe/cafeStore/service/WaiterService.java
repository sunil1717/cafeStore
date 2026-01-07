package com.sunil.cafe.cafeStore.service;

import com.sunil.cafe.cafeStore.model.Waiter;
import com.sunil.cafe.cafeStore.model.WaiterStatus;
import com.sunil.cafe.cafeStore.repository.WaiterRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WaiterService {


    private final WaiterRepository waiterRepository;


    public WaiterService(WaiterRepository waiterRepository) {
        this.waiterRepository = waiterRepository;
    }


    public Waiter addWaiter(Waiter waiter) {
        waiter.setStatus(WaiterStatus.BUSY);
        waiter.setActiveOrders(0);
        waiter.setActive(true);
        return waiterRepository.save(waiter);
    }


    public List<Waiter> getWaiters() {
        return waiterRepository.findAll();
    }



    public Map<String, Object> login(String username, String password) {

        Waiter waiter = waiterRepository
                .findByUsername(username)
                .orElse(null);

        if (waiter == null){
            Map<String, Object> error = new HashMap<>();
            error.put("success",false);
            error.put("message","user name not found");
            return error;
        }

       if (!waiter.getPassword().equals(password)){
           Map<String, Object> error = new HashMap<>();
           error.put("success",false);
           error.put("message","Invalid password");
           return error;
       }

       waiter.setStatus(WaiterStatus.FREE);
       waiterRepository.save(waiter);

       Map<String, Object> res = new HashMap<>();

        res.put("waiterId", waiter.getId());
        res.put("name", waiter.getName());
        res.put("success",true);
        res.put("message","successfully login");


        return  res;
    }

    public void removeById(String id) {
        waiterRepository.deleteById(id);
    }

    public Map<String, Object> logoutById(String id) {
        Waiter waiter = waiterRepository.findById(id).orElse(null);

        if (waiter == null){
            Map<String, Object> error = new HashMap<>();
            error.put("success",false);
            error.put("message"," not found your profile");
            return error;
        }
         waiter.setStatus(WaiterStatus.BUSY);
         waiterRepository.save(waiter);

        Map<String, Object> res = new HashMap<>();
        res.put("success",true);
        res.put("message","logout successfully");
        return res;
    }

    public List<Waiter> getAllFreeWaiter(WaiterStatus waiterStatus) {
        return waiterRepository.findByStatus(waiterStatus);
    }
}