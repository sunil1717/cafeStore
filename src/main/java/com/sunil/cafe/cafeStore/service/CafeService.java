package com.sunil.cafe.cafeStore.service;

import com.sunil.cafe.cafeStore.model.Cafe;
import com.sunil.cafe.cafeStore.repository.CafeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CafeService {


    private final CafeRepository cafeRepository;


    public CafeService(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }


    public Cafe createCafe(Cafe cafe) {
        cafe.setCreatedAt(LocalDateTime.now());
        return cafeRepository.save(cafe);
    }


    public Cafe getCafe(String id) {
        return cafeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cafe not found"));
    }
}