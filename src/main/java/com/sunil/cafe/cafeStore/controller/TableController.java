package com.sunil.cafe.cafeStore.controller;

import com.sunil.cafe.cafeStore.model.CafeTable;
import com.sunil.cafe.cafeStore.model.TableStatus;
import com.sunil.cafe.cafeStore.service.TableService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tables")
public class TableController {


    private final TableService tableService;


    public TableController(TableService tableService) {
        this.tableService = tableService;
    }


    @PostMapping
    public CafeTable add(@RequestBody CafeTable table) {
        return tableService.addTable(table);
    }


    @GetMapping("/all")
    public List<CafeTable> list() {
        return tableService.getTables();
    }

    @DeleteMapping("/remove/{id}")
    public void remove(@PathVariable String id ){
        tableService.removeById(id);
    }

    @GetMapping("/free")
    public List<CafeTable> getAllFreeTable(){
        return tableService.getAllFreeTable(TableStatus.FREE);
    }
}
