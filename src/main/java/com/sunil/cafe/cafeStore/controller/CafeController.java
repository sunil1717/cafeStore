package com.sunil.cafe.cafeStore.controller;

import com.sunil.cafe.cafeStore.model.Cafe;
import com.sunil.cafe.cafeStore.service.CafeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cafes")
public class CafeController {


    private final CafeService cafeService;


    public CafeController(CafeService cafeService) {
        this.cafeService = cafeService;
    }


    @PostMapping
    public Cafe create(@RequestBody Cafe cafe) {
        return cafeService.createCafe(cafe);
    }


    @GetMapping("/{id}")
    public Cafe get(@PathVariable String id) {
        return cafeService.getCafe(id);
    }
}
