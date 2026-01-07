package com.sunil.cafe.cafeStore.service;

import com.sunil.cafe.cafeStore.model.CafeTable;
import com.sunil.cafe.cafeStore.model.TableStatus;
import com.sunil.cafe.cafeStore.repository.CafeTableRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableService {


    private final CafeTableRepository tableRepository;


    public TableService(CafeTableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }


    public CafeTable addTable(CafeTable table) {
        table.setStatus(TableStatus.FREE);
        table.setActive(true);
        return tableRepository.save(table);
    }


    public List<CafeTable> getTables() {
        return tableRepository.findAll();
    }

    public void removeById(String id) {
        tableRepository.deleteById(id);
    }

    public List<CafeTable> getAllFreeTable( TableStatus status) {
      return  tableRepository.findByStatus(status);
    }
}