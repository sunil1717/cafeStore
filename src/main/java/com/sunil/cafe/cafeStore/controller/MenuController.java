package com.sunil.cafe.cafeStore.controller;

import com.sunil.cafe.cafeStore.model.MenuItem;
import com.sunil.cafe.cafeStore.model.menuCategory;
import com.sunil.cafe.cafeStore.service.MenuService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {


    private final MenuService menuService;


    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MenuItem add(@RequestPart("item") MenuItem item,
                        @RequestPart("image") MultipartFile image) {
        return menuService.addItem(item, image);
    }




    @GetMapping
    public List<MenuItem> list() {
        return menuService.getMenu();
    }

    @GetMapping("/all")
    public List<MenuItem> listAll() {
        return menuService.getMenuAll();
    }


    @GetMapping("/category/{category}")
    public  List<MenuItem> categoryList (@PathVariable menuCategory category){
        return menuService.findspecific(category);
    }
    @DeleteMapping("/remove/{id}")
    public  void  remove(@PathVariable String id){
        menuService.removeById(id);
    }

    @PutMapping("/switch/{id}")
    public void  changeAvailable(@PathVariable String id){
        menuService.changeStatus(id);
    }
}
